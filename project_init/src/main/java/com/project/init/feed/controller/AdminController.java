package com.project.init.feed.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.feed.dao.AdminDao;
import com.project.init.feed.dao.UserDao;
import com.project.init.feed.dto.PlanDto2;
import com.project.init.feed.dto.UserDto;
import com.project.init.util.Constant;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private AdminDao dao;
	
	
    //===== DashBoard =====
	@RequestMapping("/admin") 
	public String dash() { 
		System.out.println("dash");
		return "admin/admin_main"; 
	}
	
	@ResponseBody 
	@RequestMapping(value = "/placesDashBoard", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> placesDashBoard(HttpServletRequest request, Model model) {
		logger.info("placesDashBoard() in >>>>");
		
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		Map<String, String> map = new HashMap<>();
		map.put("value1", value1);
		map.put("value2", value2);
		
		ArrayList<PlanDto2> placesDashBoard = dao.selectDashBoardPlaces(map);
		
		request.setAttribute("topPlaces", placesDashBoard);
		System.out.println(placesDashBoard);
		
		return placesDashBoard;
	}
	
	@ResponseBody
	@RequestMapping(value = "/userDashBoard", produces="application/json; charset=UTF-8")
	public ArrayList<UserDto> userDashBoard(HttpServletRequest request, Model model){
		logger.info(" userDashBoard() in >>>>");
		
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String value3 = request.getParameter("value3");
		String value4 = request.getParameter("value4");
		Map<String, String> map = new HashMap<>();
		map.put("value1", value1);
		map.put("value2", value2);
		map.put("value3", value3);
		map.put("value4", value4);
		
		ArrayList<UserDto> userDashBoard = dao.selectDashBoardUser(map);
		
		request.setAttribute("userStatistics", userDashBoard);
		System.out.println(userDashBoard);
		
		return userDashBoard;
	}
}
