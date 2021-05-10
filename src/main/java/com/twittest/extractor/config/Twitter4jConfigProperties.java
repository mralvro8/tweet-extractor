package com.twittest.extractor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("twitter4j")
public class Twitter4jConfigProperties {
	
	private String consumerKey;
	
    private String consumerSecret;
    
    private String accessToken;
    
    private String accessTokenSecret;
    
    private Boolean debug = false;

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}
    
}
