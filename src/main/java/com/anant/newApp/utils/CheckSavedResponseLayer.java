package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static com.anant.newApp.utils.SavedResponseBucket.bucket;

public class CheckSavedResponseLayer {

    public static JSONObject getRespone(String topic) throws IOException, ParseException {
        if (bucket.containsKey(topic)){
            System.out.println("Returning Cached Topic");
            return bucket.get(topic);
        }
        System.out.println("Topic not cached, Generating and caching Topic");
        return cacheTopic(topic);
    }
    public static JSONObject cacheTopic(String topic) throws IOException, ParseException {
        NewsOrgApi newsOrgApi = new NewsOrgApi();
        JSONObject savedTopic = newsOrgApi.getSearchQuery(topic);
        bucket.put(topic, savedTopic);
        return savedTopic;
    }
}
