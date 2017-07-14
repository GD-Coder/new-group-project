package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.cooksys.secondassessment.dto.TweetUserCreateDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.dto.TweetUserDisplayNameDto;
import com.cooksys.secondassessment.dto.TweetUserDto;
import com.cooksys.secondassessment.entity.TweetUser;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface TweetUserMapper {

    @Mappings({
        @Mapping(source = "credentials.username", target = "username")
    })
	TweetUserDto tUserDto(TweetUser t);
	TweetUser toTweetUser(TweetUserDto t);
	
	TweetUser toTweetUser(TweetUserCreateDto t);
	TweetUserCreateDto tCreateDto(TweetUser t);
	
	TweetUserCredOnlyDto tweetUserCredOnlyDto(TweetUser t);
    TweetUser toTweetUser(TweetUserCredOnlyDto t);
	
    @Mappings({
        @Mapping(source = "credentials.username", target = "username")
    })
	TweetUserDisplayNameDto tweetUserDisplayNameDto(TweetUser t);
	TweetUser toTweetUser(TweetUserDisplayNameDto t);
}
