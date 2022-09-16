package com.anant.newApp.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SavedResponse {
	
	private boolean initialRequest_topHeadline = true;

	private int topHeadLineReloadCounter = 0;

	private final int topHeadLineThreshold;
	private final int savedTopicsThreshold;
	private final NewsOrgApi newsOrgApi;

	private JSONObject topHeadLineJsonCache = new JSONObject();

	//Store Topics names and their corresponding JSONObject
	private final Map<String, JSONObject> savedTopics = new HashMap<>();
	
	//Store Topics and their corresponding reloadCounter
	private final Map<String, Integer> savedTopicsReloadCounter = new HashMap<>();

	//how many topics to save
	private int savedTopicMapSize = 0;

	public SavedResponse(@Value("${savedThreshold}") int topHeadLineThreshold,
						 @Value("${savedThreshold}")int savedTopicsThreshold, @Autowired NewsOrgApi newsOrgApi) {
		this.topHeadLineThreshold = topHeadLineThreshold;
		this.savedTopicsThreshold = savedTopicsThreshold;
		this.newsOrgApi = newsOrgApi;
	}

	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						//refresh every 10 hours
						Thread.sleep(1000 * 60 * 60 * 10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					savedTopics.clear();
					savedTopicsReloadCounter.clear();
//				topHeadLineJsonCache.clear();
//				topHeadLineReloadCounter = 0;
				}
			}
		}).start();
	}

	// Stored already searched topics and their JSON data from NewsApi.org
	// and only refresh the JSON data when request for already stored query exceeds a threshold.
	public JSONObject topicQuery(String name) throws IOException, ParseException {

		if(savedTopicMapSize > 1000 /*clear map when 1000 topics are stored (why? I dont know)*/){
			savedTopics.clear();
			savedTopicsReloadCounter.clear();
			savedTopicMapSize = 0;
		}
		
		if(savedTopics.containsKey(name)){
			if(savedTopicsReloadCounter.get(name) < savedTopicsThreshold) {
				System.out.println("Cache Response and Current Threshold for: "+ name +" is " + savedTopicsReloadCounter.get(name));
				// Map contains the Query, return stored JSONObject and increment the counter
				int incrementThres = savedTopicsReloadCounter.get(name) + 1;
				savedTopicsReloadCounter.replace(name, incrementThres);
				return savedTopics.get(name);
			}else if(savedTopicsReloadCounter.get(name) >= savedTopicsThreshold){
				// counter exceeds the Threshold, refresh the JSONObject and reset the counter
				System.out.println("Refreshing the Response and Counter for: "+ name +" currentCounter is " + savedTopicsReloadCounter.get(name));
				savedTopics.replace(name, newsOrgApi.getSearchQuery(name));
				savedTopicsReloadCounter.replace(name, 0);
				System.out.println("Response Refreshed and currenThres for " + name + " is " + savedTopicsReloadCounter.get(name));
				return savedTopics.get(name);
			}		
		}else if(!savedTopics.containsKey(name)){
			// Query is not saved, add in the Map and set the counter to 0
			System.out.println("Query not Cached, Adding the Query and initializing the Reload Counter for " + name);
			savedTopics.put(name, newsOrgApi.getSearchQuery(name));
			savedTopicsReloadCounter.put(name, 0);

			++savedTopicMapSize;
		}
		return savedTopics.get(name);

	}

	public JSONObject topHeadlines() throws IOException, ParseException {
		if(topHeadLineReloadCounter < topHeadLineThreshold && !initialRequest_topHeadline) {
			System.out.println("Sending Top-Headlines cache response");
			++topHeadLineReloadCounter;
			return topHeadLineJsonCache;
		}
	    System.out.println("Sending first response or refresh response");
	    initialRequest_topHeadline = false;
		topHeadLineReloadCounter = 0;
		topHeadLineJsonCache = newsOrgApi.getTopHeadLines();
		return topHeadLineJsonCache;
	}
}
