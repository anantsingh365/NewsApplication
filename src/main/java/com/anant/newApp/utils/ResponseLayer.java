package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseLayer {

    private static final Map<String, JSONObject> savedBucket = new ConcurrentHashMap<>();
    private static boolean bucketClearingStatus = false;

    public synchronized static void ClearBucket(){
        bucketClearingStatus = true;
        savedBucket.clear();
        bucketClearingStatus = false;
    }

    public static void bucketClearingCheck(){
        while(bucketClearingStatus){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject getResponeTopic(String topic) throws IOException, ParseException{
        bucketClearingCheck();
        JSONObject newsJson = savedBucket.get(topic);
        if(newsJson != null) {
            System.out.println("saved Response Topic " + topic);
            return newsJson;
        }
        System.out.println("Topic not cached, Generating and caching Topic");
        savedBucket.putIfAbsent(topic, new NewsOrgApi().getSearchQuery(topic));
        return savedBucket.get(topic);

    }

    public static JSONObject getResponseTopHeadLines() throws IOException, ParseException {
        bucketClearingCheck();
        JSONObject newsJson = savedBucket.get("topHeadLines");
        if(newsJson != null){
            System.out.println("saved Response TopHeadLines");
            return newsJson;
        }
        savedBucket.putIfAbsent("topHeadLines",new NewsOrgApi().getTopHeadLines());
        System.out.println("New Response Top HeadLines");
        return savedBucket.get("topHeadLines");
    }
}