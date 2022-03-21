package com.project.init.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.feed.dao.IDao;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IDao dao;
	
	
	@RequestMapping("/")
	public String main() {
		logger.info("main >>>");
		
		return "main";
	}
	
	@RequestMapping("search")
	public String search(HttpServletRequest request, Model model) {
		logger.info("search >>>");
			
		int totalPosts = 2000;
		int endPage = 0;
		
		if ( totalPosts <= 20 ) {
			endPage = 1;
		}
		
		if ( totalPosts % 20 == 0 ) {
			endPage = totalPosts / 20;
		} else {
			endPage = (totalPosts / 20) + 1; 
		}
		
		request.setAttribute("startPage", 1);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPosts", totalPosts);
		
		return "search";
	}
	
	@ResponseBody
	@RequestMapping(value = "getSearchResult.do", produces = "application/text; charset=UTF-8")
	public String getSearchResult(@RequestParam String page, Model model) {
		logger.info("getSearchResult(" + page + ") in >>>");
		
		int startIdx = ((Integer.parseInt(page) - 1) * 20) + 1;
		int endIdx = Integer.parseInt(page) * 20;		
		
		logger.info(startIdx + ", " + endIdx);
		
		logger.info("getSearchResult result : " + page);
		return page;
	}
	
}
