package com.project.init.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@RequestMapping("/")
	public String index() {
		logger.info("index() in >>>>");
		return "index";
	}
	
	@RequestMapping("/join")
	public String join() {
		logger.info("index() in >>>>");
		return "join/join";
	}
	
}
