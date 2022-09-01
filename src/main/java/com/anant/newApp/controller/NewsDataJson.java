package com.anant.newApp.controller;

import com.anant.newApp.utils.SavedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


@RestController
public class NewsDataJson {


	@Autowired
	private SavedResponse savedResponse;
	
	@RequestMapping(value = "/topic-JSON{topic}")
	public JSONObject topicQuery(@RequestParam("topic") String topic) throws IOException, ParseException{
		System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
		return savedResponse.topicQuery(topic);
	}

	@RequestMapping(value = "/topHeadlines-JSON")
	public JSONObject topHeadLines() throws IOException, ParseException {
		System.out.println("Inside NewsDataJson and hash for savedResponse object is " + savedResponse.hashCode());
		return savedResponse.topHeadlines();
	}

}
