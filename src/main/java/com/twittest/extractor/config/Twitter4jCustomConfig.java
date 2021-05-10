package com.twittest.extractor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@EnableConfigurationProperties(Twitter4jConfigProperties.class)
public class Twitter4jCustomConfig {

	@Autowired
	private Twitter4jConfigProperties properties;
	
	@Bean
	@ConditionalOnMissingBean
	public TwitterFactory twitterFactory(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(properties.getDebug())
			.setOAuthConsumerKey(properties.getConsumerKey())
			.setOAuthConsumerSecret(properties.getConsumerSecret())
			.setOAuthAccessToken(properties.getAccessToken())
			.setOAuthAccessTokenSecret(properties.getAccessTokenSecret());
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf;
	}
	
	@Bean
    @ConditionalOnMissingBean
    public Twitter twitter(TwitterFactory twitterFactory){
        return twitterFactory.getInstance();
    } 
	 
}
