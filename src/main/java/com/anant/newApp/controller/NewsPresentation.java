package com.anant.newApp.controller;

import com.anant.newApp.Entity.NewsCardEntity;
import com.anant.newApp.Model.NewsCardModel;
import com.anant.newApp.Service.NewsCardService;
import com.anant.newApp.utils.ResponseLayer;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;


@Controller
@SessionAttributes({"articles","loggedInUser"})
public class NewsPresentation {

    /**
     * provides crud services to the applicaton
     * @see NewsCardService
     */
    @Autowired
    private NewsCardService newsCardService;

    @GetMapping(value = "/")
    public String landingPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        model.addAttribute("loggedInUser", auth.getName());
        DelegatingFilterProxy proxy;
        return "home";
    }

    @GetMapping(value ="/topic{topic}")
    public String topic(@RequestParam("topic") String topic, Model model, HttpServletResponse res) throws IOException, ParseException{
        res.setHeader("customHeader", "hoolhoola");
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
        Class<?> clazz = model.getAttribute("articles").getClass();
        System.out.println(clazz.getName());

        if (cards == null) {
            return "search for something first dummy";
        }

        System.out.println("saving the " + id + "article");
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