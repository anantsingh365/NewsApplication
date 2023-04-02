package com.anant.newApp.controller;

import com.mysql.cj.result.Field;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;

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

	@PostMapping("/testingByteData")
	public synchronized String  bytePrint(InputStream is) throws IOException, InterruptedException {

				File file = new File("file");
				OutputStream os = new FileOutputStream(file, true);

				System.out.println(is.transferTo(os) + " bytes appended to the file");

				is.close();
				os.close();

				System.out.println("################### A PART HAS BEEN WRITTEN ###############");
				return "dataReceived";
			}


	@PostMapping("/metaData")
			public void metaData(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String a;
		while(true){
			a  = reader.readLine();
//			if(a == null){
//				break;
//			}
			System.out.println(a);
			break;
		}

		File file = new File("file");

		if(file.renameTo(new File(a + ".pdf"))){
			System.out.println("renamed successfully");
		}
		else{
			System.out.println("couldn't rename");
		}
	}
}
