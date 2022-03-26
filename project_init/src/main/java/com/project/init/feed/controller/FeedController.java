package com.project.init.feed.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		logger.info("feed page >>>>");
		
		return "feed/main";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto> getAllPlans() {
		logger.info("getAllPlans() >>>>");
		
		return dao.selectAllPlan();
	}
	
	@ResponseBody
	@RequestMapping("insertPlan.do")
	public void insertPlan(@RequestBody PlanDto dto) {
		logger.info("insertPlans(" + dto + ") in >>>>");
		
		dao.insertPlan(dto);
		
		logger.info("insertPlans(" + dto + ") out >>>>");
	}
	
	@ResponseBody
	@RequestMapping("modify_plan.do")
	public String modifyPlan(@RequestBody PlanDto dto) {
		logger.info("modifyPlans("+ dto +") in >>>>");
		String result = dao.updatePlan(dto);
		
		logger.info("modifyPlans(" + result + ") result : " + result);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("delete_plan.do")
	public String deletePlan(@RequestBody String planNum) {
		logger.info("deletePlan("+ planNum +") in >>>>");
		String result = dao.deletePlan(planNum);
		
		logger.info("deletePlan("+ result +") result : " + result);
		return result;
	}


}
