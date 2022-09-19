package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckSavedResponseLayer {

    public static Map<String, JSONObject> savedBucket = new HashMap<>();

    public static JSONObject getResponeTopic(String topic) throws IOException, ParseException {
        if (savedBucket.containsKey(topic)){
            System.out.println("Returning Cached Topic");
            return savedBucket.get(topic);
        }
        System.out.println("Topic not cached, Generating and caching Topic");
        NewsOrgApi newsOrgApi = new NewsOrgApi();
        JSONObject savedTopic = newsOrgApi.getSearchQuery(topic);
        savedBucket.put(topic, savedTopic);
        return savedTopic;
    }
    public static JSONObject getResponseTopHeadLines() throws IOException, ParseException {
        if(savedBucket.containsKey("topHeadLines")){
            System.out.println("Saved Response Top HeadLines");
            return savedBucket.get("topHeadLines");
        }
        NewsOrgApi newsOrgApi = new NewsOrgApi();
        JSONObject topHeadLinesJSON = newsOrgApi.getTopHeadLines();
        savedBucket.put("topHeadLines",topHeadLinesJSON);
        System.out.println("New Response Top HeadLines");
        return topHeadLinesJSON;
    }
}
