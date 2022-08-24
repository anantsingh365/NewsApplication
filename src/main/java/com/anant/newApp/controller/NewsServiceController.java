package com.anant.newApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

@RestController
public class NewsServiceController {

	@Autowired
	private SavedResponse savedResponse;
	
	@RequestMapping(value = "/NewsArticle{name}")
	public JSONObject topicQuery(@RequestParam("name") String name) throws IOException, ParseException{
		return savedResponse.topicQuery(name);
	}

	@RequestMapping(value = "/Top-Headline")
	public JSONObject topHeadLines() throws IOException, ParseException {
		return savedResponse.topHeadlines();
	}
}
