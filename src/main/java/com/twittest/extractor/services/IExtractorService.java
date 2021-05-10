package com.twittest.extractor.services;

import java.util.List;

import com.twittest.extractor.domain.TweetOuput;


public interface IExtractorService {
	
	/**
	 * Extrae tweets usando el tema que se haya configurado en la propiedad {extractor.config.search-topic}
	 */
	void extraerTweets();
	
	/**
	 * Obtiene el listado de tweets de bbdd.
	 * @return lista de tweets.
	 */
	List<TweetOuput> obtenerTweets();

	/**
	 * Obtiene el listado de tweets segun el idioma de entrada.
	 * @param idioma
	 * @return lista de tweets
	 */
	List<TweetOuput> obtenerTweetsPorIdioma(String idioma);

	/**
	 * Valida el tweet con el id de entrada.
	 * @param id
	 * @return los datos del tweet validado.
	 */
	TweetOuput validar(long id);

	/**
	 * Obtiene los tweets validados que sean del usuario de entrada
	 * @param usuario
	 * @return
	 */
	List<TweetOuput> getValidados(String usuario);

	/**
	 * Obteiene los n hashtags mas usado
	 * @param num
	 * @return lista de hastagas
	 */
	List<String> listHashtags(Integer num);

}
