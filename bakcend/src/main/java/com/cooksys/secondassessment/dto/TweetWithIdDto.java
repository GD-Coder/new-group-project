package com.cooksys.secondassessment.dto;

import java.util.Date;

public class TweetWithIdDto {
	private Integer id;
	private TweetUserDisplayNameDto author;
	private String content;
	private Date posted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TweetUserDisplayNameDto getAuthor() {
		return author;
	}

	public void setAuthor(TweetUserDisplayNameDto author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}
}
