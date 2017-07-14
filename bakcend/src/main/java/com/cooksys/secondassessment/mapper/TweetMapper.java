package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.dto.TweetSimpleDto;
import com.cooksys.secondassessment.dto.TweetWithIdDto;
import com.cooksys.secondassessment.entity.Tweet;

@Mapper(componentModel = "spring", uses = {TweetUserMapper.class, ProfileMapper.class, HashTagMapper.class})
public interface TweetMapper {
	
	TweetWithIdDto tWithIdDto(Tweet t);
	Tweet toTweet(TweetWithIdDto t);
	
	TweetCreateSimpleDto tweetCreateSimpleDto(Tweet t);
	Tweet toTweet(TweetCreateSimpleDto t);
	
	TweetSimpleDto tweetSimpleDto(Tweet t);
	Tweet toTweet(TweetSimpleDto t);

}
