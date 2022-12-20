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

    private static String ApiKeyStatic;
    private static String queryStringStatic;
    private static String topHeadLinesStringStatic;

    public NewsOrgApi(@Value("${NewsOrg.ApiKey}") String ApiKey, @Value("${NewsOrg.TopHeadLineUrl}") String TopHeadLineUrl,
                      @Value("${NewsOrg.SearchQueryUrl}") String SearchQueryUrl) {
        ApiKeyStatic = ApiKey;
        topHeadLinesStringStatic = TopHeadLineUrl.replaceFirst("###API_KEY###", ApiKeyStatic);
        queryStringStatic = SearchQueryUrl.replaceFirst("###API_KEY###", ApiKeyStatic);
    }

    //get News JSON data for particular Topic
    public static JSONObject getSearchQuery(String Query) throws IOException, ParseException {
        String queryString = queryStringStatic.replaceFirst("###Enter_query_here###", Query);
        return getNewsJSON(queryString);
    }

    //get News JSON date for TopHeadLines
    public static JSONObject getTopHeadLines() throws IOException, ParseException {
        return getNewsJSON(topHeadLinesStringStatic);
    }

    private static JSONObject getNewsJSON(String url) throws IOException, ParseException {

        JSONObject jo = new JSONObject();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("Response Code :- " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Object jsonObject = new JSONParser().parse(String.valueOf(response));
            jo = (JSONObject) jsonObject;
        }
        return jo;
    }
}
