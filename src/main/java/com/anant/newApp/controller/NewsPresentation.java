package com.anant.newApp.controller;

import com.anant.newApp.Entity.NewsCardEntity;
import com.anant.newApp.Model.NewsCardModel;
import com.anant.newApp.NewAppApplication;
import com.anant.newApp.Service.NewsCardService;
import com.anant.newApp.utils.ResponseLayer;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@Controller
@SessionAttributes("articles")
public class NewsPresentation {

    /**
     * provides crud services to the applicaton
     * @see NewsCardService
     */
    @Autowired
    private NewsCardService newsCardService;

    @GetMapping(value = "/")
    public String landingPage() {
        return "home";
    }

    @PostMapping(value = "/scopeTestingLanding")
    public String scopeTestingLanding(@RequestBody JSONObject url, HttpSession session,@Autowired scopeTesting sessionObject){
        System.out.println("You entered this data" +url.toJSONString());
        System.out.println("Hashcode for session object created by spring is " + sessionObject.hashCode());
        sessionObject.jsonUrlString = url.toJSONString();
        session.setAttribute("Json Data entered by the user",sessionObject);

        return "scopeLandingPage";
    }

    @GetMapping(value = "/scopTestingSecond")
    @ResponseBody
    public String scopeTestingSecond(HttpSession session){
        scopeTesting scopeTestingObject = (scopeTesting) session.getAttribute("Json Data entered by the user");
        String string = "Welcome Back!!, You have entered the following url Data" + session.getAttribute("Json Data entered by the user").toString();
        System.out.println("Hashcode for the scopeTesting second page method should be equal to landing Page - " + scopeTestingObject.hashCode());
        System.out.println("session data is "+ scopeTestingObject.toString());
        return string;
    }

    @GetMapping(value ="/topic{topic}")
    public String topic(@RequestParam("topic") String topic, Model model) throws IOException, ParseException{
        JSONObject newsJson = ResponseLayer.getResponeTopic(topic);
        NewsCardModel.makeTopicCards(newsJson, model, topic);
        return "newsListing";
    }

    @GetMapping(value = "/topHeadLines")
    public String topHeadLines(Model model) throws IOException, ParseException {
        JSONObject newsJson = ResponseLayer.getResponseTopHeadLines();
        NewsCardModel.makeTopHeadLineCards(newsJson, model);
        return "newsListing";
    }

    /**
     * This method simply checks if the session for the user is working
     * @param model represents model which will contain saved topics
     * @param id ID, send by client to identify which article to save
     * @return
     */
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
        NewsCardModel.makeSavedCards(savedCards, model);
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
