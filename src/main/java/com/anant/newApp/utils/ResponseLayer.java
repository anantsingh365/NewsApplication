package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores the JSON data representing the news fetched from NEWSAPI.org
 * and Refreshes News Every 5 hours.
 */
@Component
public class ResponseLayer {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLayer.class);
    private static final Map<String, JSONObject> savedBucket = new ConcurrentHashMap<>();
    private static boolean bucketClearingStatus = false;

    static{
        logger.info("Init Response Bucket Refresh");
        initRefresh();
    }

    public static void initRefresh(){
        Runnable runnable = ()->{
            while(true){
                try {
                    Thread.sleep(1000 * 60 * 60 * 5);
                    // Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Clearing Response Bucket");
                ClearBucket();
                logger.info("Clearing Bucket Done");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


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
         return getNewsJson(topic);
    }

    private static JSONObject getNewsJson(String Topic) throws IOException, ParseException{

        bucketClearingCheck();
        JSONObject newsJson = savedBucket.get(Topic);
        if(newsJson != null){
            System.out.println("saved Response " + Topic);
            return newsJson;
        }
        if(Topic.equals("topHeadLines")){
            savedBucket.putIfAbsent(Topic,NewsOrgApi.getTopHeadLines());
        }else{
            savedBucket.putIfAbsent(Topic,NewsOrgApi.getSearchQuery(Topic));
        }
        System.out.println("New Response " +Topic);
        return savedBucket.get(Topic);
    }
    /**
     * Return News data in Json format for top Headlines
     * @return json data representing news for top Headlines
     * @throws IOException Http calls to newsapi.org
     * @throws ParseException parsing json is risky
     * @see NewsOrgApi
     */
    public static JSONObject getResponseTopHeadLines() throws IOException, ParseException {
        return getNewsJson("topHeadLines");
    }
}
