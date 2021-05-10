package com.twittest.extractor.transformers;

import com.twittest.extractor.domain.TweetInput;
import com.twittest.extractor.domain.TweetOuput;
import com.twittest.extractor.model.DatosTweet;

import twitter4j.Status;

public interface ITweetTransformer {
	
	
	TweetOuput fromModelToOuput(DatosTweet model);

	DatosTweet fromInputToModel(Status input);

}
