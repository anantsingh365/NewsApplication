package com.anant.newApp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NewsOrgApi {

	private static final String USER_AGENT = "Mozilla/5.0";

	private final String ApiKey;
	private final String TopHeadLineUrl;
    private final String SearchQueryUrl;

    public NewsOrgApi(@Value("${NewsOrg.ApiKey}") String ApiKey, @Value("${NewsOrg.TopHeadLineUrl}") String TopHeadLineUrl,
                      @Value("${NewsOrg.SearchQueryUrl}")String SearchQueryUrl){
        this.ApiKey = ApiKey;
        this.TopHeadLineUrl = TopHeadLineUrl;
        this.SearchQueryUrl = SearchQueryUrl;
    }


	public JSONObject getSearchQuery(String Query) throws IOException, ParseException {
		String queryString = SearchQueryUrl.replaceFirst("###Enter-query-here###",Query);
		String queryStringWithKey = queryString.replaceFirst("###API_KEY###", ApiKey);
		return getNewsApi(queryStringWithKey);
	}
	
	public JSONObject getTopHeadLines() throws IOException, ParseException {
		String topHeadLineWithKey = TopHeadLineUrl.replaceFirst("###API_KEY###", ApiKey);
		return getNewsApi(topHeadLineWithKey);
	}
	
	private JSONObject getNewsApi(String url) throws IOException, ParseException {
	
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
