package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SavedNewsBucket {
    private final Map<String, JSONObject> savedTopics = new HashMap<>();
    private static SavedNewsBucket bucket;
    public boolean containsTopic(String topic) {
        return savedTopics.containsKey(topic);
    }
    public JSONObject getTopic(String string) {
        return savedTopics.get(string);
    }
    public void insertTopic(String topic, JSONObject jo){
        savedTopics.put(topic, jo);
    }
    private SavedNewsBucket(){}
    public synchronized static SavedNewsBucket getSavedNewsBucket(){
        if(bucket == null){
            return bucket = new SavedNewsBucket();
        }
        return bucket;
    }
    public JSONObject generateNews(String topic){
          NewsOrgApi newsOrgApi = new NewsOrgApi();
          JSONObject jo = new JSONObject();
        try {
            jo = newsOrgApi.getSearchQuery(topic);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        savedTopics.put(topic,jo);
        return jo;
    }
}
