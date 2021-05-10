package com.twittest.extractor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twittest.extractor.domain.TweetOuput;
import com.twittest.extractor.services.IExtractorService;

@RestController
@RequestMapping(value = "/extractor")
public class ExtractorController {
	
	@Autowired
	IExtractorService service;
	
	/**
	 * Devuelve una lista de todos los tweets que hay en bbdd.
	 * @return List<TweetOuput> tweets.
	 */
	@GetMapping(value = "/tweets")
	private List<TweetOuput> getTweets() {
		return service.obtenerTweets();
	}
	
	/**
	 * Devuelve una lista de todos los tweets que hay en bbdd segun el idioma de entrada.
	 * @param idioma
	 * @return List<TweetOuput> tweets
	 */
	@GetMapping(value = "/tweets/{idioma}", produces = MediaType.APPLICATION_JSON_VALUE)
	private List<TweetOuput> getTweetsPorIdioma(@PathVariable String idioma) {
		return service.obtenerTweetsPorIdioma(idioma);
	}
	
	/**
	 * Valida el tweet cuyo id se ha especificado.
	 * @param id
	 * @return TweetOuput tweetData
	 */
	@PostMapping(value = "/validar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private TweetOuput validar(@PathVariable long id) {
		return service.validar(id);
	}
	
	/**
	 * Obtiene los tweets validados del usuario que se requiere.
	 * @param usuario
	 * @return List<TweetOuput> tweets.
	 */
	@GetMapping(value = "/getValidados/{usuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	private List<TweetOuput> getValidados(@PathVariable String usuario) {
		return service.getValidados(usuario);
	}
	
	/**
	 * Devuelve una lista de los n hashtags mas usados en los tweets guardados en bbdd.
	 * @param num
	 * @return List<String> hashtags
	 */
	@GetMapping(value = "/listHashtags/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
	private List<String> listHashtags(@PathVariable int num) {
		return service.listHashtags(num);
	}
	
	/**
	 * Devuelve una lista de los hashtags mas usados en los tweets guardados en bbdd usando el tamaño por defecto.
	 * @param num
	 * @return List<String> hashtags
	 */
	@GetMapping(value = "/listHashtags", produces = MediaType.APPLICATION_JSON_VALUE)
	private List<String> listHashtagsDefault() {
		return service.listHashtags(null);
	}

}
