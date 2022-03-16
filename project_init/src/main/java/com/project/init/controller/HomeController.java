package com.project.init.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String main() {
		logger.info("main >>>");
		
		return "main";
	}
	
	@RequestMapping("search")
	public String search(Model model) {
		logger.info("search >>>");
		
		int totalPostCount = 20;
		int pagePostCount = 20;
		int pageNum = 0;
		
		if ( totalPostCount % pagePostCount == 0 ) {
			pageNum = totalPostCount / pagePostCount;
		} else if ( totalPostCount % pagePostCount > 0 ) {
			pageNum = totalPostCount / pagePostCount + 1;
		} else if ( totalPostCount < pagePostCount ) {
			pageNum = 1;
		}
		
		logger.info(pageNum+"");
			
		model.addAttribute("pagePostCount", pagePostCount);
		model.addAttribute("pageNum", pageNum);
		
		return "search";
	}
	
}
