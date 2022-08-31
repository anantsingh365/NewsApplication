package com.anant.newApp.controller;

import com.anant.newApp.Model.MakeModel;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
public class NewsPresentation {

    @Autowired
    private NewsDataJson newsDataJson;

    @RequestMapping(value ="/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value ="/topic{topic}", method = RequestMethod.GET)
//    @ResponseBody
    public String topic(@RequestParam("topic") String topic, Model model) throws IOException, ParseException {
        String pageTitle = "Showing Articles for: "+topic;
        MakeModel.makeModel(newsDataJson.topicQuery(topic), model,pageTitle);
        return "newsListing";
    }

    @RequestMapping(value = "/topHeadLines")
    public String topHeadLines(Model model) throws IOException, ParseException {
        String pageTitle = "Showing Top Headlines";
        MakeModel.makeModel(newsDataJson.topHeadLines(), model,pageTitle);
        return "newsListing";
    }
}
