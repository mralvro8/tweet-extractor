package com.twittest.extractor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.twittest.extractor.model.DatosTweet;

public interface ExtractorRepository extends CrudRepository<DatosTweet, Long> {
	
	@Query(value = "SELECT TWEET_ID FROM DATOS_TWEET ORDER BY TWEET_ID DESC LIMIT 1", nativeQuery = true)
	Long getLastTweetId();

	List<DatosTweet> findByIdioma(String idioma);
	
	@Query("SELECT dt FROM  DatosTweet dt WHERE dt.validado IS TRUE AND dt.usuario LIKE %?1%")
	List<DatosTweet> findValidadosByuser(String user);
	
	@Query(value = "SELECT texto FROM DATOS_TWEET WHERE texto LIKE '%#%'", nativeQuery = true)
	List<String> findAllWithHashtag();
}
