package com.anant.newApp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.anant.newApp.NewsArticle;

public class CacheResponse {
	
	private boolean initialRequest_topHeadline = true;

	private int topHeadLineReloadCounter = 0;

	private final int topHeadLineReloadThreshold = 40;
	private final int cacheTopicsReloadThreshold = 40;

	private JSONObject topHeadLineJsonCache;

	//Store Topics names and their corresponding JSONObject
	private final Map<String, JSONObject> cacheTopics = new HashMap<String, JSONObject>();
	
	//Store Topics and their corresponding reloadCounter
	private final Map<String, Integer> cacheTopicsReloadCounter = new HashMap<String, Integer>();

	private int cacheTopicMapSize = 0;

	// Stored already searched topics and their JSON data from NewsApi.org
	// and only refresh the JSON data when request for already stored query exceeds a threshold.
	public JSONObject topicQuery(String name) throws IOException, ParseException {

		if(cacheTopicMapSize > 1000){
			cacheTopics.clear();
			cacheTopicsReloadCounter.clear();
			cacheTopicMapSize = 0;
		}
		
		if(cacheTopics.containsKey(name)){
			if(cacheTopicsReloadCounter.get(name) < cacheTopicsReloadThreshold) {
				System.out.println("Cache Response and Current Threshold for: "+ name +" is " + cacheTopicsReloadCounter.get(name));
				// Map contains the Query, return stored JSONObject and increment the counter
				int incrementThres = cacheTopicsReloadCounter.get(name) + 1;
				cacheTopicsReloadCounter.replace(name, incrementThres);
				return cacheTopics.get(name);	
			}else if(cacheTopicsReloadCounter.get(name) >= cacheTopicsReloadThreshold){
				// counter exceeds the Threshold, refresh the JSONObject and reset the counter
				System.out.println("Refreshing the Response and Threshold for: "+ name +" currentThres is " + cacheTopicsReloadCounter.get(name));
				cacheTopics.replace(name, NewsArticle.getSearchQuery(name));
				cacheTopicsReloadCounter.replace(name, 0);
				System.out.println("Response Refreshed and currenThres for " + name + " is " + cacheTopicsReloadCounter.get(name));
				return cacheTopics.get(name);
			}		
		}else if(!cacheTopics.containsKey(name)){
			// Query is not cached, add in the Map and set the counter to 0
			System.out.println("Query not Cached, Adding the Query and initializing the Reload Counter for " + name);
			cacheTopics.put(name, NewsArticle.getSearchQuery(name));
			cacheTopicsReloadCounter.put(name, 0);

			++cacheTopicMapSize;
		}
		return cacheTopics.get(name);

	}

	public JSONObject topHeadlines() throws IOException, ParseException {
		if(topHeadLineReloadCounter < topHeadLineReloadThreshold && !initialRequest_topHeadline) {
			System.out.println("Sending Top-Headlines cache response");
			++topHeadLineReloadCounter;
			return topHeadLineJsonCache;
		}
	    System.out.println("Sending first response or refresh response");
	    initialRequest_topHeadline = false;
		topHeadLineReloadCounter = 0;
		topHeadLineJsonCache = NewsArticle.getTopHeadLines();
		return topHeadLineJsonCache;
	}
}
