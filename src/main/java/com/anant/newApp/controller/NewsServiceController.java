package com.anant.newApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;


import com.anant.newApp.*;

@RestController
public class NewsServiceController {
		
	private CacheResponse cacheResponse = new CacheResponse();
	
	@RequestMapping(value = "/NewsArticle{name}")
	public JSONObject topicQuery(@RequestParam("name") String name) throws IOException, ParseException{
	
		return cacheResponse.topicQuery(name);
	}
	
	
	@RequestMapping(value = "/Top-Headline")
	public JSONObject topHeadLines() throws IOException, ParseException {
		
		return cacheResponse.topHeadlines();
	}
}
