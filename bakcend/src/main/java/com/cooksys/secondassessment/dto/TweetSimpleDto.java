package com.cooksys.secondassessment.dto;

import java.util.Date;

public class TweetSimpleDto {
	private Integer id;
	private String content;
	private TweetUserDisplayNameDto author;
	private Date posted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TweetUserDisplayNameDto getAuthor() {
		return author;
	}

	public void setAuthor(TweetUserDisplayNameDto author) {
		this.author = author;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

}
