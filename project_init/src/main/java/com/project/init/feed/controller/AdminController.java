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
	
	// 장소 별 통계
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
	
	// 년도, 월, 일 별 회원수 통계
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
	
	// 회원 성별 통계
	@ResponseBody
	@RequestMapping(value = "/userDashBoardGender", produces="application/json; charset=UTF-8")
	public ArrayList<UserDto> userDashBoardGender(Model model){
		logger.info("userDashBoardGender() in >>>>");
		
		ArrayList<UserDto> userDashBoardGender = dao.selectDashBoardUserGender();
		model.addAttribute("userGender", userDashBoardGender);
		
		return userDashBoardGender;
	}
	
	// 회원 연령별 통계
	@ResponseBody
	@RequestMapping(value = "/userDashBoardAge", produces="application/json; charset=UTF-8")
	public ArrayList<UserDto> userDashBoardAge(Model model){
		logger.info("userDashBoardAge() in >>>>");
		
		ArrayList<UserDto> userDashBoardAge = dao.selectDashBoardUserAge();
		model.addAttribute("userAge", userDashBoardAge);
		
		return userDashBoardAge;
	}
}
