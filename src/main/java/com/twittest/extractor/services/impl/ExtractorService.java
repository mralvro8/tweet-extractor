package com.twittest.extractor.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.twittest.extractor.config.CustomConfigProperties;
import com.twittest.extractor.domain.TweetOuput;
import com.twittest.extractor.model.DatosTweet;
import com.twittest.extractor.repository.ExtractorRepository;
import com.twittest.extractor.services.IExtractorService;
import com.twittest.extractor.transformers.impl.TweetTransformer;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class ExtractorService implements IExtractorService {
	
	Logger logger = LoggerFactory.getLogger(ExtractorService.class);
	
	static final int NUM_TWEETS = 300;
	
	@Autowired
	private CustomConfigProperties config;
	
	@Autowired
	private Twitter twitter;
	
	@Autowired
	private TweetTransformer transformer;
	
	@Autowired
	private ExtractorRepository repository;

	/**
	 * Extrae tweets cada 5 minutos.
	 * El tema de la busqueda esta configurado en la propiedad {extractor.config.search-topic}.
	 */
	@Scheduled(fixedDelay = 300000)
	@Override
	public void extraerTweets() {
		logger.info("ExtractorService - Iniciado proceso de extracción de tweets");
		List<Status> listaTweets = new ArrayList<>();
		
		Query query = new Query(config.getSearchTopic());
		Long lastId = repository.getLastTweetId();
		if (lastId != null) {
			query.setSinceId(lastId);
		};
		
		try {
			// Por cada idioma hace una busqueda ya que la api no deja buscar por varios idiomas en una misma busqueda.
			for (String lang : config.getIdiomas()) {
				int tempCount = 0;
				
					query.setLang(lang);
					QueryResult res;
					do {
						res = twitter.search(query);
						// Filtra los resultados por numero de seguidores y los añade a la lista de tweets a guardar.
						listaTweets.addAll(
								res.getTweets()
								.stream()
								.filter(tweet -> tweet.getUser().getFollowersCount() > config.getMinSeguidores() && !tweet.isRetweet())
								.collect(Collectors.toList())
							);
						tempCount += res.getTweets().size();
						query = res.nextQuery();
					} while(res.hasNext() && tempCount <= NUM_TWEETS);
				
			}
		} catch (TwitterException e) {
			logger.error("ExtractorService - Error al obtener tweets - {}", e);
		}
		logger.info("ExtractorService - Insertados {} tweets a la bbdd", listaTweets.size());
		//Recorremos la lista de tweets, los transfromamos a nuestro modelo y los guardamos.
		repository.saveAll(listaTweets.stream().map(tweet -> transformer.fromInputToModel(tweet)).collect(Collectors.toList()));
	}
	

	@Override
	public List<TweetOuput> obtenerTweets() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).map(transformer::fromModelToOuput).collect(Collectors.toList());
	}


	@Override
	public List<TweetOuput> obtenerTweetsPorIdioma(String idioma) {
		return repository.findByIdioma(idioma).stream().map(transformer::fromModelToOuput).collect(Collectors.toList());
	}


	@Override
	public TweetOuput validar(long id) {
		Optional<DatosTweet> optionalTweet = repository.findById(id);
		if (optionalTweet.isPresent()) {
			DatosTweet tweet = optionalTweet.get();
			tweet.setValidado(true);
			return transformer.fromModelToOuput(repository.save(tweet));
		}
		return null;
	}


	@Override
	public List<TweetOuput> getValidados(String usuario) {
		return repository.findValidadosByuser(usuario).stream().map(transformer::fromModelToOuput).collect(Collectors.toList());
	}
	
	private List<String> getHashtagsFromtext(String str) {
		Pattern HASH_PATTERN = Pattern.compile("#(\\S+)");
		Matcher mat = HASH_PATTERN.matcher(str);
		List<String> hashtagsList = new ArrayList<String>();
		while (mat.find()) {
			hashtagsList.add(mat.group(1));
		}
		return hashtagsList;
	}
	
	@Override
	public List<String> listHashtags(Integer num) {
		Integer tamLista = num != null ? num : config.getNumHashtags();
		List<String> textosTweet = repository.findAllWithHashtag();
		List<String> hashtags = new ArrayList<String>();
		// cRecorremos los tweets con hastgas y devolvemos una lista de todos
		for (String string : textosTweet) {
			hashtags.addAll(getHashtagsFromtext(string));
		}
		
		// Contamos las ocurrencias de cada hastag
		Map<String, Long> map = hashtags.stream()
		        .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
		
		//Guardamos el hastags y el nbumero de ocurrencias por orden.
		List<Map.Entry<String, Long>> result = map.entrySet().stream()
		        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		        .limit(10)
		        .collect(Collectors.toList());
		
		//Devolvemos los hastags limitando el tamaño de la lista segun la entrada.
		return result.stream().map(entry -> entry.getKey()).collect(Collectors.toList()).subList(0, tamLista);
	}

}
