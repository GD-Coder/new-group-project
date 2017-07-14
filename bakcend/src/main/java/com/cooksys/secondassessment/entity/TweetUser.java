package com.cooksys.secondassessment.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class TweetUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Embedded
	private Credentials credentials;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Profile profile;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date joined;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<TweetUser> followersOfUser = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<TweetUser> userFollowing = new HashSet<>();

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private Set<Tweet> tweets = new HashSet<>();

	private Boolean isActive = true;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Tweet> likedTweets = new HashSet<>();
	
	@ManyToMany(mappedBy="mentions", fetch = FetchType.LAZY)
	private Set<Tweet> tweetsMentionedUser = new HashSet<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Date getJoined() {
		return joined;
	}

	public void setJoined(Date joined) {
		this.joined = joined;
	}

	public Set<TweetUser> getFollowersOfUser() {
		return followersOfUser;
	}

	public void setFollowersOfUser(Set<TweetUser> followersOfUser) {
		this.followersOfUser = followersOfUser;
	}

	public Set<TweetUser> getUserFollowing() {
		return userFollowing;
	}

	public void setUserFollowing(Set<TweetUser> userFollowing) {
		this.userFollowing = userFollowing;
	}

	public Set<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<Tweet> getLikedTweets() {
		return likedTweets;
	}

	public void setLikedTweets(Set<Tweet> likedTweets) {
		this.likedTweets = likedTweets;
	}

	public Set<Tweet> getTweetsMentionedUser() {
		return tweetsMentionedUser;
	}

	public void setTweetsMentionedUser(Set<Tweet> tweetsMentionedUser) {
		this.tweetsMentionedUser = tweetsMentionedUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweetUser other = (TweetUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
