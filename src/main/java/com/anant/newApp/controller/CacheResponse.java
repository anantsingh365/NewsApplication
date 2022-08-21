package com.anant.newApp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.anant.newApp.NewsArticle;

public class CacheResponse {
	
	private boolean initialRequest_topHeadline = true;
	private boolean initialRequest_newsArticle = true;
	
	private int topHeadLineReloadCounter = 0;
	
//	enum ReloadThresHold{
//		topHeadLineReloadThresHold(5),
//		cachTopicsReloadThresHold(5);
//		
//		int thres; 
//		
//		ReloadThresHold(int thres){
//			this.thres = thres;
//		}
//		 
//	}
//	
	
	private final int topHeadLineReloadThreshold = 8;
	private final int cacheTopicsReloadThreshold = 8; 
	
	private JSONObject topHeadLineJson;
	private JSONObject newsArticleJson;
	
	private Set<String> newsArticleList = new HashSet<String>();
	
	//Store Topics names and their corresponding JSONObject
	private Map<String, JSONObject> cacheTopics = new HashMap<String, JSONObject>();
	
	//Store Topics and their corresponding reloadCounter
	private Map<String, Integer> cacheTopicsReloadCounter = new HashMap<String, Integer>();

	public JSONObject topicQuery(String name) throws IOException, ParseException {
		
		if(cacheTopics.containsKey(name)){
			if(cacheTopicsReloadCounter.get(name) < cacheTopicsReloadThreshold) {
				System.out.println("Cache Response and Current Threshold for: "+ name +" is " + cacheTopicsReloadCounter.get(name));
				int incrementThres = cacheTopicsReloadCounter.get(name) + 1;
				cacheTopicsReloadCounter.replace(name, incrementThres);
				return cacheTopics.get(name);	
			}else if(cacheTopicsReloadCounter.get(name) >= cacheTopicsReloadThreshold){
				System.out.println("Refreshing the Response and Threshold for: "+ name +" currentThres is " + cacheTopicsReloadCounter.get(name));
				cacheTopics.replace(name, NewsArticle.getSearchQuery(name));
				cacheTopicsReloadCounter.replace(name, 0);
				System.out.println("Response Refreshed and currenThres for " + name + " is " + cacheTopicsReloadCounter.get(name));
				return cacheTopics.get(name);
			}		
		}else if(!cacheTopics.containsKey(name)){
			System.out.println("Query not Cached, Adding the Query and initializing the Reload Counter for " + name);
			cacheTopics.put(name, NewsArticle.getSearchQuery(name));
			cacheTopicsReloadCounter.put(name, 0);
		}
		return cacheTopics.get(name);

	}

	public JSONObject topHeadlines() throws IOException, ParseException {
		if(topHeadLineReloadCounter < topHeadLineReloadThreshold && !initialRequest_topHeadline) {
			System.out.println("Sending Top-Headlines cache response");
			++topHeadLineReloadCounter;
			return topHeadLineJson;
		}
	    System.out.println("Sending first response or refresh response");
	    initialRequest_topHeadline = false;
		topHeadLineReloadCounter = 0;
		topHeadLineJson = NewsArticle.getTopHeadLines();
		return topHeadLineJson;
	}
}
