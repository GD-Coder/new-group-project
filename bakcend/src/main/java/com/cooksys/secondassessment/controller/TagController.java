package com.cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.HashTagNoIdDto;
import com.cooksys.secondassessment.dto.TweetWithIdDto;
import com.cooksys.secondassessment.mapper.HashTagMapper;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.service.TagService;

@RestController
@RequestMapping("tag")
public class TagController {
	
	private TagService tService;
	private HashTagMapper hMapper;
	private TweetMapper tweetMapper;

	public TagController(TagService tService, HashTagMapper hMapper, TweetMapper tweetMapper) {
		this.tService = tService;
		this.hMapper = hMapper;
		this.tweetMapper = tweetMapper;
	}

	@GetMapping("validate/tag/exists/{label}")
	public boolean tagExists(@PathVariable String label, HttpServletResponse response) {
		return tService.tagExists(label);
	}
	
	@GetMapping("tags")
	public List<HashTagNoIdDto> getAll(HttpServletResponse response) {
		return tService.getAll().stream()
				.map(hMapper::hashTagNoIdDto)
				.collect(Collectors.toList());
	}
	
	@GetMapping("tags/{label}")
	public List<TweetWithIdDto> getTag(@PathVariable String label, HttpServletResponse response) {
		return tService.getTweetsWithTag(label).stream()
				.filter(tweet -> tweet.getIsDeleted().equals(false))
				.sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				.map(tweetMapper::tWithIdDto)
				.collect(Collectors.toList());
	}

}
