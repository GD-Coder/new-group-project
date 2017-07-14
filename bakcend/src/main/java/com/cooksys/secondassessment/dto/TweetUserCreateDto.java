package com.cooksys.secondassessment.dto;

import com.cooksys.secondassessment.entity.Credentials;

public class TweetUserCreateDto {

	private Credentials credentials;
	private ProfileEmailOnlyDto profile;

	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public ProfileEmailOnlyDto getProfile() {
		return profile;
	}
	public void setProfile(ProfileEmailOnlyDto profile) {
		this.profile = profile;
	}



}
