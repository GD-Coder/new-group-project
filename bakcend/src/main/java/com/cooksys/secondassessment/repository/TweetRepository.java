package com.cooksys.secondassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
	
	List<Tweet> findByInReplyTo_IdOrderByPostedDesc(Integer id);
	List<Tweet> findByRepostOf_IdOrderByPostedDesc(Integer id);
	List<Tweet> findByAuthor_IdOrderByPostedDesc(Integer id);
	
	//Finds by two levels deep
	List<Tweet> findByInReplyTo_RelatedTweets_IdOrderByPostedAsc(Integer id);
	//Finds by one level deep
	List<Tweet> findByRelatedTweets_Id(Integer id);
	
}
