package com.anant.newApp.Model;

import com.anant.newApp.Entity.NewsCardEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


public class NewsCardModel {

    private static final int noOfCardsTopic = 100;
    private int ArticleId;
    private String title;
    private String description;
    private String publishedAt;
    private String urlToImage;
    private String url;

    private static void newsDataModel(JSONObject jo, Model model, String topic, List<NewsCardModel> cards){

        var joArray = (JSONArray) jo.get("articles");
        if(joArray == null){
            System.out.println("Couldn't find the news for the given topic");
        }
        JSONObject joArticles;
        int numOfCards = joArray.size();
        for(int i =0; i<numOfCards; i++){
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
    }

    public static void makeSavedCards(List<NewsCardEntity> savedCards, Model model){
        for(int i=0; i<savedCards.size();i++) {
            savedCards.get(i).setId(i);
        }
        model.addAttribute("savedArticles",savedCards);
        model.addAttribute("pageTitle","saved Topics");
    }

    public static List<NewsCardModel> getCards(){
        var cards = new ArrayList<NewsCardModel>();
        for(int i =0;i<noOfCardsTopic;i++){
            cards.add(new NewsCardModel());
        }
        return cards;
    }

    public static void makeTopicCards(JSONObject jo, Model model, String topic){
        String pageTitle = "Showing HeadLines For "+ topic;

        var cards = getCards();
        newsDataModel(jo, model, pageTitle, cards);
    }

    public static void makeTopHeadLineCards(JSONObject jo, Model model){
        String pageTitle = "Showing Top HeadLines";

        var cards = getCards();
        newsDataModel(jo, model, pageTitle, cards);
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
