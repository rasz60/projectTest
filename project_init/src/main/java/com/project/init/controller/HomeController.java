package com.project.init.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.project.init.feed.dto.PlanDto2;

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
	
	@RequestMapping("home")
	public String home() {
		logger.info("home in >>>");
		
		return "home";
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
	
	@ResponseBody //main 맵에 전체 마커 표시
	@RequestMapping(value = "/selectPlanList", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> selectPlanList(Model model) {
		logger.info("selectPlanList() in >>>>");
		ArrayList<PlanDto2> selectPlanList = dao.selectPlanList();
		model.addAttribute("latlng", selectPlanList);
		System.out.println(selectPlanList);
		return selectPlanList;
	}
	
	
	@ResponseBody //main 맵 필터
	@RequestMapping(value = "/filter", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> filter(HttpServletRequest request, Model model){
		logger.info("filter() in >>>>");
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String value3 = request.getParameter("value3");
		String value4 = request.getParameter("value4");
		Map<String, String> map = new HashMap<>();
		map.put("value1", value1);
		map.put("value2", value2);
		map.put("value3", value3);
		map.put("value4", value4);
		ArrayList<PlanDto2> filter = dao.filter(map);

		request.setAttribute("filter", filter);
		return filter;
	}
	
}
