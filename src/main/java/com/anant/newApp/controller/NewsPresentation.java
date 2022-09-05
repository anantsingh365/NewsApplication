package com.anant.newApp.controller;

import com.anant.newApp.Model.NewsCardModel;
import com.anant.newApp.utils.SavedResponse;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
public class NewsPresentation {

    static int noOfcardsTopic = 100;
    static int noOfCardsTopHeadLines = 20;
    @Autowired
    private SavedResponse savedResponse;

    @RequestMapping(value ="/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value ="/topic{topic}", method = RequestMethod.GET)
    public String topic(@RequestParam("topic") String topic, Model model) throws IOException, ParseException {
        NewsCardModel.newsDataModelTopic(savedResponse.topicQuery(topic), model,topic);
        System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
        return "newsListing";
    }

    @RequestMapping(value = "/topHeadLines")
    public String topHeadLines(Model model) throws IOException, ParseException {
        NewsCardModel.newsDataModelTopHeadLines(savedResponse.topHeadlines(), model);
        System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
        return "newsListing";
    }
}
