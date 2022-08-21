package com.anant.newApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NewsArticle {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String TOP_HEADLINE = "https://newsapi.org/v2/top-headlines?country=us&apiKey=9e3acd0313cc4776945ca6c1d0a2190e";
	private static final String QUERY_ARTICLES = "https://newsapi.org/v2/everything?q=###Enter-query-here###&apiKey=9e3acd0313cc4776945ca6c1d0a2190e";	
	
	public static JSONObject getSearchQuery(String Query) throws IOException, ParseException {
		String queryString = QUERY_ARTICLES.replaceFirst("###Enter-query-here###",Query);
		return getNewsApi(queryString);
	}
	
	public static JSONObject getTopHeadLines() throws IOException, ParseException {
		return getNewsApi(TOP_HEADLINE);
	}
	
	
	private static JSONObject getNewsApi(String url) throws IOException, ParseException {
	
		JSONObject jo = new JSONObject();
		URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Object jsonObject = new JSONParser().parse(String.valueOf(response));

            jo  = (JSONObject) jsonObject; 
            
	} return jo;
}
}
