package com.cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.TweetUserCreateDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.dto.TweetUserDto;
import com.cooksys.secondassessment.dto.TweetWithIdDto;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.mapper.TweetUserMapper;
import com.cooksys.secondassessment.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private UserService uService;
	private TweetUserMapper tMapper;
	private TweetMapper tweetMapper;

	public UserController(UserService uService, TweetUserMapper tMapper, TweetMapper tweetMapper) {
		this.uService = uService;
		this.tMapper = tMapper;
		this.tweetMapper = tweetMapper;
	}
	
	//Checks whether or not a given username exists.
	@GetMapping("validate/username/exists/@{username}")
	public boolean exists(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.exists(username);
	}
	
	//Checks whether or not a given username is available.
	@GetMapping("validate/username/available/@{username}")
	public boolean available(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return !uService.exists(username);
	}
	
	//Retrieves all active (non-deleted) users as an array.
	@GetMapping("users")
	public List<TweetUserDto> getAll(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.getAll().stream()
				.filter(user -> user.getIsActive().equals(true))
				.map(tMapper::tUserDto)
				.collect(Collectors.toList());
	}
	
	@PostMapping("users")
	public TweetUserDto create(@RequestBody TweetUserCreateDto user,
			@RequestParam(required = false) String firstName, 
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String phone,
			HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return tMapper.tUserDto(uService.save(tMapper.toTweetUser(user), firstName, lastName, phone));
		
	}
	
	@GetMapping("users/@{username}")
	public TweetUserDto getUser(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return tMapper.tUserDto(uService.getUser(username));
	}
	
	@PatchMapping("users/@{username}")
	public TweetUserDto updateUser(@RequestBody TweetUserCredOnlyDto user, 
			@RequestParam(required = false) String firstName, 
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String phone,
			@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return tMapper.tUserDto(uService.updateAUser(user, username, firstName, lastName, phone));
	}
	
	@DeleteMapping("users/@{username}")
	public TweetUserDto deleteUser(@RequestBody TweetUserCredOnlyDto creds,  @PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return tMapper.tUserDto(uService.delete(username, tMapper.toTweetUser(creds)));
	}
	
	//Subscribes the user whose credentials are provided by the request body to 
	//the user whose username is given in the url
	@PostMapping("users/@{username}/follow")
	public void followUser(@RequestBody TweetUserCredOnlyDto creds, @PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		uService.followUser(username, creds);
		
	}
	
	@PostMapping("users/@{username}/unfollow")
	public void unfollowUser(@RequestBody TweetUserCredOnlyDto creds, @PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		uService.unfollowUser(username, creds);
	}
	
	@GetMapping("users/@{username}/feed")
	public List<TweetWithIdDto> getUsersFeed(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return uService.getUsersFeed(username).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				.map(tweetMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("users/@{username}/tweets")
	public List<TweetWithIdDto> getUserTweets(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return uService.getUserTweets(username).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.map(tweetMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("users/@{username}/mentions")
	public List<TweetWithIdDto> getUserMentions(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return uService.getMentions(username).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				.map(tweetMapper::tWithIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("users/@{username}/followers")
	public List<TweetUserDto> getUserFollowers(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return uService.getFollowers(username).stream()
				.filter(user -> user.getIsActive().equals(true))
				.map(tMapper::tUserDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("users/@{username}/following")
	public List<TweetUserDto> getUserFollowing(@PathVariable String username, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		return uService.getUserFollowing(username).stream()
				.filter(user -> user.getIsActive().equals(true))
				.map(tMapper::tUserDto)
				.collect(Collectors.toList());
	}
	
	@PostMapping("users/validate/user")
	public TweetUserDto validateAUser(@RequestBody TweetUserCredOnlyDto tweetUser, HttpServletResponse response) {
		return tMapper.tUserDto(uService.checkUserCredentials(tweetUser.getCredentials()));
	}
}
