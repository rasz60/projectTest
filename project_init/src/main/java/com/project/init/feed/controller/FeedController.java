package com.project.init.feed.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.feed.dao.IDao;
import com.project.init.feed.dto.PlanDto;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private IDao dao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping("")
	public String feed() {
		return "feed/main";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto> getAllPlans() {
		return dao.selectAllPlan();
	}
	
	@ResponseBody
	@RequestMapping("insertPlan.do")
	public void insertPlan(@RequestBody PlanDto dto) {
		dao.insertPlan(dto);
	}	
}
