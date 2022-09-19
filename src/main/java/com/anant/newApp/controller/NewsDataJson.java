package com.anant.newApp.controller;

import com.anant.newApp.utils.SavedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.anant.newApp.utils.CheckSavedResponseLayer;

@RestController
public class NewsDataJson {

	@Autowired
	private SavedResponse savedResponse;
	
	@RequestMapping(value = "/topic-JSON{topic}")
	public JSONObject topicQuery(@RequestParam("topic") String topic) throws IOException, ParseException{
		return CheckSavedResponseLayer.getResponeTopic(topic);
	}

	@RequestMapping(value = "/topHeadlines-JSON")
	public JSONObject topHeadLines() throws IOException, ParseException {
		return CheckSavedResponseLayer.getResponseTopHeadLines();
	}
}
