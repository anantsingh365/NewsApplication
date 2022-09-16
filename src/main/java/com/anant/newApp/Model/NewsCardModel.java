package com.anant.newApp.Model;

import com.anant.newApp.Entity.NewsCardEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


public class NewsCardModel {

    private static final int noOfCardsTopic = 100;
    private static final int noOfCardsTopHeadLines = 20;
    private static final List<NewsCardModel> CARDS_OBJECTS_TOPIC = new ArrayList<>();
    private static final List<NewsCardModel> CARDS_OBJECTS_TOP_HEADLINES = new ArrayList<>();
    private static boolean cardsList = false;


    private int ArticleId;
    private String title;
    private String description;
    private String publishedAt;
    private String urlToImage;
    private String url;

    private static void makeCardsObjects(){
        if(!cardsList){
            for(int i =0;i<noOfCardsTopic;i++){
                CARDS_OBJECTS_TOPIC.add(new NewsCardModel());
            }
            for(int i=0;i<noOfCardsTopHeadLines;i++){
                CARDS_OBJECTS_TOP_HEADLINES.add(new NewsCardModel());
            }
            cardsList = true;
        }
    }

    private static void newsDataModel(JSONObject jo, Model model, String topic, List<NewsCardModel> cards){
        if(!cardsList){
            makeCardsObjects();
        }
        var joArray = (JSONArray) jo.get("articles");
        JSONObject joArticles;
        for(int i =0; i<cards.size(); i++){
            //get Articles from JSON array and add the articles in the list

            joArticles = (JSONObject) joArray.get(i);
            cards.get(i).setArticleId(i);
            cards.get(i).setTitle((String)joArticles.get("title"));
            cards.get(i).setDescription((String)joArticles.get("description"));
            cards.get(i).setPublishedAt((String)joArticles.get("publishedAt"));
            cards.get(i).setUrlToImage((String) joArticles.get("urlToImage"));
            cards.get(i).setUrl((String) joArticles.get("url"));
        }
        model.addAttribute("articles",cards);
        model.addAttribute("pageTitle",topic);
//        model.addAttribute("endOfPage","")
    }

    public static void newsDataModelSaved(List<NewsCardEntity> savedCards, Model model){
        ArrayList<NewsCardModel> cards = new ArrayList<>();
        savedCards.forEach((saved) -> {
            NewsCardModel tempCard = new NewsCardModel();
            tempCard.setTitle(saved.getTitle());
            tempCard.setDescription(saved.getDescription());
            tempCard.setPublishedAt(saved.getPublishedAt());
            tempCard.setUrlToImage(saved.getUrlToImage());
            tempCard.setUrl(saved.getUrl());
            cards.add(tempCard);
        });
        model.addAttribute("articles",cards);
        model.addAttribute("pageTitle","saved Topics");
    }

    public static void newsDataModelTopic(JSONObject jo, Model model, String topic){
        String pageTitle = "Showing HeadLines For "+ topic;
        newsDataModel(jo, model, pageTitle, CARDS_OBJECTS_TOPIC);
    }

    public static void newsDataModelTopHeadLines(JSONObject jo, Model model){
        String pageTitle = "Showing Top HeadLines";
        newsDataModel(jo, model, pageTitle, CARDS_OBJECTS_TOP_HEADLINES);
    }

    public NewsCardModel(){
    }

    public int getArticleId() {
        return ArticleId;
    }

    public void setArticleId(int articleId) {
        this.ArticleId = articleId;
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
