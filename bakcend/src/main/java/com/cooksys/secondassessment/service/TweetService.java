package com.cooksys.secondassessment.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetCreateSimpleDto;
import com.cooksys.secondassessment.dto.TweetUserCredOnlyDto;
import com.cooksys.secondassessment.entity.Context;
import com.cooksys.secondassessment.entity.HashTag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.TweetUser;
import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.exception.InvalidArgumentPassedException;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.repository.HashTagRepository;
import com.cooksys.secondassessment.repository.TweetRepository;

@Service
public class TweetService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private TweetRepository tRepo;
	private UserService uService;
	private HashTagRepository hRepo;
	private TweetMapper tMapper;

	public TweetService(TweetRepository tRepo, UserService uService, HashTagRepository hRepo, TweetMapper tMapper) {
		this.tRepo = tRepo;
		this.uService = uService;
		this.hRepo = hRepo;
		this.tMapper = tMapper;
	}
	
	public List<Tweet> getAll() {
		return tRepo.findAll();
	}

	public Tweet createSimpleTweet(TweetCreateSimpleDto tweet) {
		TweetUser tweetUser = uService.checkUserCredentials(tweet.getCredentials());
		
		if (tweetUser != null) {
			Tweet tweetCrea = new Tweet();
			tweetCrea.setContent(tweet.getContent());
			tweetCrea.setAuthor(tweetUser);
			tweetCrea.setMentions(parseUsersFromTweet(tweet.getContent(), tweetCrea.getMentions()));
			tweetCrea.setLabels(parseHashTagsFromTweet(tweet.getContent(), tweetCrea.getLabels()));
			return tRepo.save(tweetCrea);
		}
		
		throw new InvalidArgumentPassedException();
	}
	
	private Set<TweetUser> parseUsersFromTweet(String contents, Set<TweetUser> tweetUsers) {	
		String[] split = contents.split(" ");
		
		for(String sp : split) {
			if (sp.startsWith("@")) {
				String username = sp.substring(1);
				log.debug(username);
				TweetUser tweetUser = uService.getUser(username);
				if (tweetUser != null && tweetUser.getIsActive().equals(true)) {
					tweetUsers.add(tweetUser);
				}
			}
		}
		
		return tweetUsers;
	}
	
	private Set<HashTag> parseHashTagsFromTweet(String contents, Set<HashTag> hashTags) {
		String[] split = contents.split(" ");
		
		for(String sp : split) {
			if (sp.startsWith("#")) {
				String label = sp.substring(1);
				log.debug(label);
				if (hRepo.findByLabelEquals(label) != null) {
					HashTag hashTag = hRepo.findByLabel(label);
					hashTag.setLastUsed(Calendar.getInstance().getTime());
					hashTags.add(hRepo.save(hashTag));
				} else {
					HashTag hashTag = new HashTag();
					hashTag.setLabel(label);
					hashTags.add(hRepo.save(hashTag));
				}
			}
		}
		
		return hashTags;
	}

	public Tweet getById(Integer id) {
		Tweet tweet = tRepo.findOne(id);
		if (tweet != null) {
			return tweet;			
		}
		
		throw new EntityNotFoundException();
	}

	public Tweet deleteById(Integer id) {
		if (tRepo.exists(id)) {
			Tweet tweet = getById(id);
			tweet.setIsDeleted(true);
			return tweet;
		}
		
		throw new EntityNotFoundException();
	}

	public Set<HashTag> getTagsFromTweet(Integer id) {
		Tweet tweet = tRepo.findOne(id); 
		if (tweet != null) {
			return tweet.getLabels();
		}
		
		throw new EntityNotFoundException();
	}

	public Set<TweetUser> getUsersMentioned(Integer id) {
		Tweet tweet = tRepo.findOne(id);
		if (tweet != null) {
			return tweet.getMentions();
		}
		
		throw new EntityNotFoundException();
	}

	public void likeTweetById(TweetUserCredOnlyDto creds, Integer id) {
		TweetUser tweetUser = uService.checkUserCredentials(creds.getCredentials());
		
		Tweet tweet = tRepo.findOne(id);
		
		if (tweetUser != null && tweet != null) {
			tweetUser.getLikedTweets().add(tweet);
			uService.save(tweetUser);
		} else {
			throw new EntityNotFoundException();
		}
	
	}

	public Set<TweetUser> getLikesForTweetById(Integer id) {
		Tweet tweet = tRepo.getOne(id);
		if (tweet != null) {
			return tweet.getUsersWhoLiked();
		}
		
		throw new EntityNotFoundException();
	}

	public Tweet replyToTweetById(TweetCreateSimpleDto simpleDto, Integer id) {
		TweetUser tweetUser = uService.checkUserCredentials(simpleDto.getCredentials());
		Tweet replyToTweet = getById(id);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true) && replyToTweet.getIsDeleted().equals(false)) {
			Tweet tweet = createSimpleTweet(simpleDto);
			tweet.setInReplyTo(replyToTweet);
			tweet.getRelatedTweets().add(replyToTweet);
			return tRepo.save(tweet);
		}
		
		throw new EntityNotFoundException();
	}

	public List<Tweet> getDirectReplies(Integer id) {
		List<Tweet> tweets = tRepo.findByInReplyTo_IdOrderByPostedDesc(id); 
		if (tweets != null) {
			return tweets;
		}
		throw new EntityNotFoundException();
	}

	public Tweet repostTweetById(TweetUserCredOnlyDto creds, Integer id) {
		TweetUser tweetUser = uService.checkUserCredentials(creds.getCredentials());
		Tweet tweetToRepost = tRepo.findOne(id);
		
		if (tweetUser != null && tweetUser.getIsActive().equals(true) && tweetToRepost.getIsDeleted().equals(false)) {
			TweetCreateSimpleDto tweetCreateSimpleDto = new TweetCreateSimpleDto();
			tweetCreateSimpleDto.setCredentials(tweetUser.getCredentials());
			tweetCreateSimpleDto.setContent(tweetToRepost.getContent());
			
			Tweet tweet = createSimpleTweet(tweetCreateSimpleDto);
			tweet.setRepostOf(tweetToRepost);
			return tRepo.save(tweet);
		}
		
		throw new EntityNotFoundException();
	}
	
	public List<Tweet> getDirectReposts(Integer id) {
		List<Tweet> tweets = tRepo.findByRepostOf_IdOrderByPostedDesc(id);
		if (tweets != null) {
			return tweets;
		}
		
		throw new EntityNotFoundException();
	}

	public Context getContextOfTweetById(Integer id) {
		Context context = new Context();
		Tweet tweet = tRepo.findOne(id);
		context.setOrginialTweet(tMapper.tWithIdDto(tweet));
		
		//Traverses one level deep with this method
		List<Tweet> tweetsTest = tRepo.findByInReplyTo_RelatedTweets_IdOrderByPostedAsc(id);
		context.setTweetsAfter(tweetsTest.stream()
				.map(tMapper::tWithIdDto).collect(Collectors.toSet()));

		return context;
	}
	
}
