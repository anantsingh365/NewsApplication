package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CheckSavedResponseLayer {

    private static final Map<String, JSONObject> savedBucket = new ConcurrentHashMap<>();
    private static boolean bucketClearingStatus = false;

    public synchronized static void ClearBucket(){
        bucketClearingStatus = true;
        savedBucket.clear();
        bucketClearingStatus = false;
    }

    public static void bucketClearingUnderway(){
        while(bucketClearingStatus){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject getResponeTopic(String topic) throws IOException, ParseException{
        bucketClearingUnderway();
        JSONObject newsJson = savedBucket.get(topic);
        if(newsJson != null) {
            System.out.println("saved Response Topic " + topic);
            return newsJson;
        }
        System.out.println("Topic not cached, Generating and caching Topic");
        NewsOrgApi newsOrgApi = new NewsOrgApi();
        JSONObject savedTopic = newsOrgApi.getSearchQuery(topic);
        savedBucket.put(topic, savedTopic);
        return savedTopic;

    }

    public static JSONObject getResponseTopHeadLines() throws IOException, ParseException {
        bucketClearingUnderway();
        JSONObject newsJson = savedBucket.get("topHeadLines");
        if(newsJson != null){
            System.out.println("saved Response TopHeadLines");
            return newsJson;
        }
        NewsOrgApi newsOrgApi = new NewsOrgApi();
        JSONObject topHeadLinesJSON = newsOrgApi.getTopHeadLines();
        savedBucket.put("topHeadLines",topHeadLinesJSON);
        System.out.println("New Response Top HeadLines");
        return topHeadLinesJSON;
    }
}
