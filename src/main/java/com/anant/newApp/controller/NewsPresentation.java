package com.anant.newApp.controller;

import com.anant.newApp.Entity.NewsCardEntity;
import com.anant.newApp.Model.NewsCardModel;
import com.anant.newApp.Service.NewsCardService;
import com.anant.newApp.utils.SavedResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("articles")
public class NewsPresentation {

    @Autowired
    private SavedResponse savedResponse;

    @Autowired
    private NewsCardService newsCardService;

    @GetMapping(value = "/")
    public String landingPage() {
        return "home";
    }

    @GetMapping(value ="/topic{topic}")
    public String topic(@RequestParam("topic") String topic, Model model) throws IOException, ParseException {
        JSONObject newsJson = savedResponse.topicQuery(topic);
        NewsCardModel.newsDataModelTopic(newsJson, model,topic);
        System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
        return "newsListing";
    }

    @GetMapping(value = "/topHeadLines")
    public String topHeadLines(Model model) throws IOException, ParseException {
        var newsJson = savedResponse.topHeadlines();
        NewsCardModel.newsDataModelTopHeadLines(newsJson, model);
        System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
        return "newsListing";
    }

    @GetMapping(value = "/saveArticle{id}")
    @ResponseBody
    public String savedArticles(Model model, @RequestParam int id) {
        if (model == null) {
            return "no Item to save";
        }
        var cards = (ArrayList<NewsCardModel>) model.getAttribute("articles");
        System.out.println("saving the " + id + "article");
        if (cards == null) {
            return "No id Provided";
        }
        System.out.println(cards.get(id).getArticleId() + cards.get(id).getDescription() + cards.get(id).getTitle() + cards.get(id).getUrlToImage());
        var newsCard = cards.get(id);
        var entity = new NewsCardEntity();

        entity.setTitle(newsCard.getTitle());
        entity.setDescription(newsCard.getDescription());
        entity.setPublishedAt(newsCard.getPublishedAt());
        entity.setUrlToImage(newsCard.getUrlToImage());
        entity.setUrl(newsCard.getUrl());
        newsCardService.save(entity);
        return "saved the Article with id " + id;
    }

    @GetMapping(value = "/saved")
    public String getSavedArticles(Model model) {
        var savedCards = newsCardService.findAll();
        NewsCardModel.newsDataModelSavedArticles(savedCards, model);
        return "savedArticles";
    }

    @PostMapping(value = "/deleteArticle")
    @ResponseBody
    public String deleteSavedArticle(@RequestBody JSONObject url) {
        if(newsCardService.deleteSavedArticle((String)url.get("url"))){
            return "Article with url: " + url + " Deleted";
        }
        return "Article with url: "+url+" Not saved, hence cannot deleted";
    }
}
