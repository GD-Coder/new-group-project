package com.cooksys.secondassessment.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.exception.UsernameExistsException;
import com.cooksys.secondassessment.mapper.TweetUserMapper;
import com.cooksys.secondassessment.repository.TweetRepository;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private TweetRepository tRepository;
	
	public UserService(UserRepository userRepository, TweetUserMapper tMapper, TweetRepository tRepository) {
		this.userRepository = userRepository;
		this.tRepository = tRepository;
	}

	public boolean exists(String username) {
		return userRepository.findByCredentials_UsernameEquals(username) != null;
	}
	
	public List<TweetUser> getAll() {
		return userRepository.findAll();
	}

	public TweetUser save(TweetUser user, String firstName, String lastName, String phone) {
		
		//Checks if a user is created
		if (exists(user.getCredentials().getUsername())) {
			//Gets the user if the user was created
			TweetUser tUser = checkUserCredentials(user.getCredentials());
			
			//If the user was deleted will reactive the user
			if (tUser != null && tUser.getIsActive().equals(false)) {
				tUser.setIsActive(true);
				return userRepository.save(tUser);
			}
			
			throw new UsernameExistsException();
		} 
		
		user.getProfile().setFirstName(firstName);
		user.getProfile().setLastName(lastName);
		user.getProfile().setPhone(phone);
		
		//If all else fails then it creates a new user
		return userRepository.save(user);
	}
	
	public TweetUser save(TweetUser tweetUser) {
		return userRepository.save(tweetUser);
	}

	
	public TweetUser updateAUser(TweetUserCredOnlyDto user, String username, String firstName, String lastName, String phone) {
		TweetUser tweetUser = checkUserCredentials(user.getCredentials());
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true) 
				&& tweetUser.getCredentials().getUsername().equals(username)) {
			
			if (firstName != null) {
				tweetUser.getProfile().setFirstName(firstName);
			}
			
			if (lastName != null) {
				tweetUser.getProfile().setLastName(lastName);
			}
			
			
			if (phone != null) {
				tweetUser.getProfile().setPhone(phone);
			}
			
			return userRepository.save(tweetUser);
		}
		
		throw new EntityNotFoundException();
	}

	public TweetUser getUser(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_UsernameEquals(username);
		if (tweetUser != null) {
			return tweetUser;
		}
		
		throw new EntityNotFoundException();

	}

	public TweetUser delete(String username, TweetUser creds) {
		TweetUser user = checkUserCredentials(creds.getCredentials());	
		if (user != null && username.equals(user.getCredentials().getUsername())) {
			user.setIsActive(false);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException();
		}
		
		return user;
	}

	public void followUser(String username, TweetUserCredOnlyDto creds) {
		TweetUser user = checkUserCredentials(creds.getCredentials());	
		
		if (user != null && exists(username) && user.getIsActive().equals(true) 
				&& !user.getCredentials().getUsername().equals(username)) {
			
			TweetUser userToFollow = getUser(username);
			
			if (!userToFollow.getFollowersOfUser().contains(user) && userToFollow.getIsActive().equals(true)) {
				userToFollow.getFollowersOfUser().add(user);
				userRepository.save(userToFollow);
			}
			
			if (!user.getUserFollowing().contains(userToFollow) && userToFollow.getIsActive().equals(true)) {
				user.getUserFollowing().add(userToFollow);
				userRepository.save(user);
			}
			
		} else {
			throw new EntityNotFoundException();
		}
	}

	public void unfollowUser(String username, TweetUserCredOnlyDto creds) {
		TweetUser user = checkUserCredentials(creds.getCredentials());	
		
		if (user != null && exists(username) && user.getIsActive().equals(true) 
				&& !user.getCredentials().getUsername().equals(username)) {
			
			TweetUser userToFollow = getUser(username);
			
			if (userToFollow.getFollowersOfUser().contains(user) && userToFollow.getIsActive().equals(true)) {
				userToFollow.getFollowersOfUser().remove(user);
				userRepository.save(userToFollow);
			}
			
			if (user.getUserFollowing().contains(userToFollow) && userToFollow.getIsActive().equals(true)) {
				user.getUserFollowing().remove(userToFollow);
				userRepository.save(user);
			}
			
		} else {
			throw new EntityNotFoundException();
		}
		
	}

	public Set<TweetUser> getFollowers(String username) {
		if (exists(username)) {
			return getUser(username).getFollowersOfUser();
		}
		
		throw new EntityNotFoundException();
	}

	public Set<TweetUser> getUserFollowing(String username) {
		if (exists(username)) {
			return getUser(username).getUserFollowing();
		}
		
		throw new EntityNotFoundException();
	}

	public Set<Tweet> getMentions(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_UsernameEquals(username);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
			return tweetUser.getTweetsMentionedUser();
		}
		
		throw new EntityNotFoundException();
	}

	public List<Tweet> getUserTweets(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_UsernameEquals(username);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
			return tRepository.findByAuthor_IdOrderByPostedDesc(tweetUser.getId());
		}
		
		throw new EntityNotFoundException();
	}

	public List<Tweet> getUsersFeed(String username) {
		TweetUser tweetUser = userRepository.findByCredentials_UsernameEquals(username);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
			//Gets all users the current user is following that are active
			Set<TweetUser> tweetUsers = tweetUser.getUserFollowing().stream()
					.filter(user -> user.getIsActive().equals(true))
					.collect(Collectors.toSet());
			//Adds the current user to list to parse out tweets
			tweetUsers.add(tweetUser);
			List<Tweet> tweetU = new ArrayList<>();

			//Cycles through the list of users to parse tweets
			for(TweetUser tweetUser2 : tweetUsers) {
				//Parses all tweets from the user to create the feed
				Iterator<Tweet> tIterator = tweetUser2.getTweets().iterator();
				while(tIterator.hasNext()) {
					tweetU.add(tIterator.next());
				}
			}
			
			return tweetU;
		}
		
		throw new EntityNotFoundException();
	}
	
	public TweetUser checkUserCredentials(Credentials user) {
		return userRepository
				.findByCredentials_UsernameEqualsAndCredentials_PasswordEquals(
						user.getUsername(), user.getPassword());
	}
	
}
