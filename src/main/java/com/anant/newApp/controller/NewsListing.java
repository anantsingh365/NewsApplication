package com.anant.newApp.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@Controller
public class NewsListing {

    @Autowired
    private NewsServiceController newsServiceController;

    @RequestMapping(value ="/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value ="/topic{topic}", method = RequestMethod.GET)
//    @ResponseBody
    public String topic(@RequestParam("topic") String topic, Model model) throws IOException, ParseException {
        makeModel(newsServiceController.topicQuery(topic), model);
        return "newsListing";
    }

    @RequestMapping(value = "/topHeadLines")
    public String topHeadLines(Model model) throws IOException, ParseException {
        makeModel(newsServiceController.topHeadLines(), model);
        return "newsListing";
    }

    private void makeModel(JSONObject jo, Model model){
        JSONArray joArray = (JSONArray) jo.get("articles");
        JSONObject joArticles = (JSONObject) joArray.get(10);
        model.addAttribute("source",joArticles.get("source"));
        model.addAttribute("title",joArticles.get("title"));
        model.addAttribute("description", joArticles.get("description"));
        model.addAttribute("publishedAt",joArticles.get("publishedAt"));
        model.addAttribute("urlToImage",joArticles.get("urlToImage"));
        model.addAttribute("url",joArticles.get("url"));
    }
}
