package com.twittest.extractor.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("extractor.config")
public class CustomConfigProperties {
	
	private Integer minSeguidores;
	
	private List<String> idiomas;
	
	private Integer numHashtags;
	
	private String searchTopic;

	public Integer getMinSeguidores() {
		return minSeguidores;
	}

	public void setMinSeguidores(Integer minSeguidores) {
		this.minSeguidores = minSeguidores;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public Integer getNumHashtags() {
		return numHashtags;
	}

	public void setNumHashtags(Integer numHashtags) {
		this.numHashtags = numHashtags;
	}

	public String getSearchTopic() {
		return searchTopic;
	}

	public void setSearchTopic(String searchTopic) {
		this.searchTopic = searchTopic;
	}
	
}
