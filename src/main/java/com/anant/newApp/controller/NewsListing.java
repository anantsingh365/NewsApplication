package com.anant.newApp.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
    public String topic(@RequestParam("topic") String topic) throws IOException, ParseException {
        return "newsListing";
       // return newsServiceController.topicQuery(topic);
    }

    @RequestMapping(value = "topHeadlines")
    public String topHeadLines(){
        return "newsListing";
    }

}
