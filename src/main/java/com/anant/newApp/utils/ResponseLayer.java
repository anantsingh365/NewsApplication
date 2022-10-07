package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores the JSON data representing the news fetched from NEWSAPI.org
 */
@Component
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

    /**
     * Return news data in json format for given topic
     * @param topic topic for which user search for
     * @return JSONObject containing news data
     * @throws IOException Http calls to newsapi.org
     * @throws ParseException parsing json is risky
     * @see NewsOrgApi
     */
    public static JSONObject getResponeTopic(String topic) throws IOException, ParseException{
        bucketClearingCheck();
        JSONObject newsJson = savedBucket.get(topic);
        if(newsJson != null) {
            System.out.println("saved Response Topic " + topic);
            return newsJson;
        }
        //context.getBean(NewsOrgApi.class);
        System.out.println("Topic not cached, Generating and caching Topic");
        savedBucket.putIfAbsent(topic,  NewsOrgApi.getSearchQuery(topic));
        return savedBucket.get(topic);
    }

    /**
     * Return News data in Json format for top Headlines
     * @return json data representing news for top Headlines
     * @throws IOException Http calls to newsapi.org
     * @throws ParseException parsing json is risky
     * @see NewsOrgApi
     */
    public static JSONObject getResponseTopHeadLines() throws IOException, ParseException {
        bucketClearingCheck();
        JSONObject newsJson = savedBucket.get("topHeadLines");
        if(newsJson != null){
            System.out.println("saved Response TopHeadLines");
            return newsJson;
        }
        savedBucket.putIfAbsent("topHeadLines",NewsOrgApi.getTopHeadLines());
        System.out.println("New Response Top HeadLines");
        return savedBucket.get("topHeadLines");
    }
}
