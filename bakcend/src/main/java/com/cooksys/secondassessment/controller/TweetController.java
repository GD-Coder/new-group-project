package com.cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.HashTagNoIdDto;
import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.dto.TweetSimpleDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.dto.TweetUserDto;
import com.cooksys.secondassessment.dto.TweetWithIdDto;
import com.cooksys.secondassessment.entity.Context;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.mapper.HashTagMapper;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.mapper.TweetUserMapper;
import com.cooksys.secondassessment.service.TweetService;

@RestController
@RequestMapping("tweet")
public class TweetController {
	
	private TweetService tService;
	private TweetMapper tMapper;
	private HashTagMapper hMapper;
	private TweetUserMapper tUserMapper;

	public TweetController(TweetService tService, TweetMapper tMapper, HashTagMapper hMapper, TweetUserMapper tUserMapper) {
		this.tService = tService;
		this.tMapper = tMapper;
		this.hMapper = hMapper;
		this.tUserMapper = tUserMapper;
	}
	
	@GetMapping("tweets")
	public List<TweetWithIdDto> getAll(HttpServletResponse response) {
		return tService.getAll()
				.stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false) && tweet.getAuthor().getIsActive().equals(true))
				.sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				.map(tMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@PostMapping("tweets")
	public TweetSimpleDto createSimpTweet(@RequestBody TweetCreateSimpleDto tweet, HttpServletResponse response) {
		return tMapper.tweetSimpleDto(tService.createSimpleTweet(tweet));
	}
	
	@GetMapping("tweets/{id}")
	public Tweet getTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getById(id);
	}
	
	@DeleteMapping("tweets/{id}")
	public Tweet deleteTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.deleteById(id);
	}
	
	@PostMapping("tweets/{id}/like")
	public void likeTweetById(@RequestBody TweetUserCredOnlyDto creds ,@PathVariable Integer id, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		tService.likeTweetById(creds, id);
	}
	
	@PostMapping("tweets/{id}/reply")
	public TweetWithIdDto replyToTweetById(@RequestBody TweetCreateSimpleDto simpleDto, @PathVariable Integer id, HttpServletResponse response) {
		return tMapper.tWithIdDto(tService.replyToTweetById(simpleDto, id));
	}
	
	@PostMapping("tweets/{id}/repost")
	public TweetWithIdDto repostTweetById(@RequestBody TweetUserCredOnlyDto creds, @PathVariable Integer id, HttpServletResponse response) {
		return tMapper.tWithIdDto(tService.repostTweetById(creds, id));
	}
	
	@GetMapping("tweets/{id}/tags")
	public List<HashTagNoIdDto> getTagsForTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getTagsFromTweet(id)
				.stream()
				.map(hMapper::hashTagNoIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("tweets/{id}/likes")
	public List<TweetUserDto> getUsersForTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getLikesForTweetById(id).stream()
				.filter(user -> user.getIsActive().equals(true))
				.map(tUserMapper::tUserDto).collect(Collectors.toList());
	}
	
	@GetMapping("tweets/{id}/context")
	public Context getContext(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getContextOfTweetById(id);
	}
	
	@GetMapping("tweets/{id}/replies")
	public List<TweetWithIdDto> getRepliesToTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getDirectReplies(id).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false) && tweet.getAuthor().getIsActive().equals(true))
				.sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				.map(tMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("tweets/{id}/reposts")
	public List<TweetWithIdDto> getRepostsForTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getDirectReposts(id).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false) && tweet.getAuthor().getIsActive().equals(true))
				.sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				.map(tMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("tweets/{id}/mentions")
	public List<TweetUserDto> getUserMentionedInTweetById(@PathVariable Integer id, HttpServletResponse response) {
		return tService.getUsersMentioned(id)
				.stream()
				.filter(user -> user.getIsActive().equals(true))
				.map(tUserMapper::tUserDto)
				.collect(Collectors.toList());
	}
}
