package com.cooksys.secondassessment.entity;

import java.util.HashSet;
import java.util.Set;

import com.cooksys.secondassessment.dto.TweetWithIdDto;

public class Context {
	private TweetWithIdDto orginialTweet;
	private Set<TweetWithIdDto> tweetsAfter = new HashSet<>();
	private Set<TweetWithIdDto> tweetsBefore = new HashSet<>();

	public TweetWithIdDto getOrginialTweet() {
		return orginialTweet;
	}

	public void setOrginialTweet(TweetWithIdDto orginialTweet) {
		this.orginialTweet = orginialTweet;
	}

	public Set<TweetWithIdDto> getTweetsAfter() {
		return tweetsAfter;
	}

	public void setTweetsAfter(Set<TweetWithIdDto> tweetsAfter) {
		this.tweetsAfter = tweetsAfter;
	}

	public Set<TweetWithIdDto> getTweetsBefore() {
		return tweetsBefore;
	}

	public void setTweetsBefore(Set<TweetWithIdDto> tweetsBefore) {
		this.tweetsBefore = tweetsBefore;
	}

}
