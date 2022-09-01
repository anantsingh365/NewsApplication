package com.anant.newApp.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class NewsCardModel {

    private String title;
    private String description;
    private String publishedAt;
    private String urlToImage;
    private String url;

    public static void newsDataModel(JSONObject jo, Model model, String topic){
        String pageTitle = "Showing Articles for: "+topic;
        JSONArray joArray = (JSONArray) jo.get("articles");
        JSONObject joArticles;
        List<NewsCardModel> articles = new ArrayList<>();
        NewsCardModel newsCardModel;

        int noOfArticles = joArray.size();
        for(int i =0; i<noOfArticles; i++){
            //get Articles from JSON array and add the articles in the list
            joArticles = (JSONObject) joArray.get(i);
            newsCardModel = new NewsCardModel();
            newsCardModel.setTitle((String)joArticles.get("title"));
            newsCardModel.setDescription((String)joArticles.get("description"));
            newsCardModel.setPublishedAt((String)joArticles.get("publishedAt"));
            newsCardModel.setUrlToImage((String) joArticles.get("urlToImage"));
            newsCardModel.setUrl((String) joArticles.get("url"));
            articles.add(newsCardModel);
        }
        model.addAttribute("articles",articles);
        model.addAttribute("pageTitle",pageTitle);
    }

    public static void newsDataModel(JSONObject jo, Model model){
        String pageTitle = "Showing Top HeadLines";
        newsDataModel(jo, model, pageTitle);
    }

    public NewsCardModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
