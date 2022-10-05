package com.anant.newApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.asm.ClassWriter;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores the JSON data representing the news fetched from NEWSAPI.org
 * in the free tire you only get 100 requests per day, so saving news
 * makes sense.
 * @author Anant Singh
 */
public class ResponseLayer {

    private static final Map<String, JSONObject> savedBucket = new ConcurrentHashMap<>();
    private static boolean bucketClearingStatus = false;

    public synchronized static void ClearBucket(){
        bucketClearingStatus = true;
        savedBucket.clear();
        bucketClearingStatus = false;
    }

    /**
     * Information about this control flow graph edge.
     *
     * <ul>
     *    <li>{@link NewsOrgApi#getSearchQuery(String)} returns the news JSON for particular query that the client has entered. </li>
     *   <li>If {@link ClassWriter#COMPUTE_MAXS} is used, this field contains either a stack size
     *       delta (for an edge corresponding to a jump instruction), or the value EXCEPTION (for an
     *       edge corresponding to an exception handler). The stack size delta is the stack size just
     *       after the jump instruction, minus the stack size at the beginning of the predecessor
     *       basic block, i.e. the one containing the jump instruction.
     *   <li>If {@link ClassWriter#COMPUTE_FRAMES} is used, this field contains either the value JUMP
     *       (for an edge corresponding to a jump instruction), or the index, in the {@link
     *       ClassWriter} type table, of the exception type that is handled (for an edge corresponding
     *       to an exception handler).
     * </ul>
     */
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
     * @param topic topic for which user search for (never {@code null})
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
        System.out.println("Topic not cached, Generating and caching Topic");
        savedBucket.putIfAbsent(topic, new NewsOrgApi().getSearchQuery(topic));
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
        savedBucket.putIfAbsent("topHeadLines",new NewsOrgApi().getTopHeadLines());
        System.out.println("New Response Top HeadLines");
        return savedBucket.get("topHeadLines");
    }
}
