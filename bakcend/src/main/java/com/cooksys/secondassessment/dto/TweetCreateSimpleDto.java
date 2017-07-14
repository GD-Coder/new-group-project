package com.cooksys.secondassessment.dto;

import com.cooksys.secondassessment.entity.Credentials;

public class TweetCreateSimpleDto {
	
	String content;
	Credentials credentials;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
