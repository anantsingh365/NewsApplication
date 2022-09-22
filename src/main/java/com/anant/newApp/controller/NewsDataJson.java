package com.anant.newApp.controller;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.anant.newApp.utils.ResponseLayer;

@RestController
public class NewsDataJson {

	@RequestMapping(value = "/topic-JSON{topic}")
	public JSONObject topicQuery(@RequestParam("topic") String topic) throws IOException, ParseException{
		return ResponseLayer.getResponeTopic(topic);
	}

	@RequestMapping(value = "/topHeadlines-JSON")
	public JSONObject topHeadLines() throws IOException, ParseException {
		return ResponseLayer.getResponseTopHeadLines();
	}
}
