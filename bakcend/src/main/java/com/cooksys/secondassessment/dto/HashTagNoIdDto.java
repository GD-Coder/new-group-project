package com.cooksys.secondassessment.dto;

import java.util.Date;

public class HashTagNoIdDto {
	private String label;
	private Date firstUsed;
	private Date lastUsed;

	public Date getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(Date firstUsed) {
		this.firstUsed = firstUsed;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
