package com.anant.newApp.controller;

import com.anant.newApp.Model.NewsCardModel;
import com.anant.newApp.utils.SavedResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
public class NewsPresentation {

    @Autowired
    private SavedResponse savedResponse;

    private JSONObject newsJson;

    @GetMapping(value="/")
    public String landingPage(){
        return "home";
    }

    @RequestMapping(value ="/topic{topic}", method = RequestMethod.GET)
    public String topic(@RequestParam("topic") String topic, Model model) throws IOException, ParseException {
        newsJson = savedResponse.topicQuery(topic);
        NewsCardModel.newsDataModelTopic(newsJson, model,topic);
        System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
        return "newsListing";
    }

    @RequestMapping(value = "/topHeadLines")
    public String topHeadLines(Model model) throws IOException, ParseException {
        newsJson = savedResponse.topHeadlines();
        NewsCardModel.newsDataModelTopHeadLines(newsJson, model);
        System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
        return "newsListing";
    }
}
