package com.twittest.extractor.transformers.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twittest.extractor.domain.TweetOuput;
import com.twittest.extractor.model.DatosTweet;
import com.twittest.extractor.transformers.ITweetTransformer;

import twitter4j.Status;

@Service
public class TweetTransformer implements ITweetTransformer {

	@Override
	public DatosTweet fromInputToModel(Status input) {
		DatosTweet model =  new DatosTweet();
		model.setTexto(input.getText());
		//A veces no viene la geolocalizacion del tweet.
		Optional.ofNullable(input.getGeoLocation()).ifPresent(location -> model.setLocalizacion(location.toString()));
		model.setUsuario(input.getUser().getScreenName());
		model.setTweetId(input.getId());
		model.setIdioma(input.getLang());
		return model;
	}

	@Override
	public TweetOuput fromModelToOuput(DatosTweet model) {
		TweetOuput out = new TweetOuput();
		out.setId(model.getId());
		out.setIdioma(model.getIdioma());
		out.setTexto(model.getTexto());
		out.setUsuario(model.getUsuario());
		out.setLocalizacion(model.getLocalizacion());
		out.setValidado(model.isValidado());
		return out;
	}

}
