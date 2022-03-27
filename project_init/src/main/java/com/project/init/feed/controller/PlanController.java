package com.project.init.feed.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.feed.dao.IDao;
import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;


@Controller
@RequestMapping("/plan")
public class PlanController {

	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private IDao dao;
	

	@RequestMapping("")
	public String planmstDo(PlanDto dto, Model model) {
		logger.info("planmst.do(" + dto.getPlanName() + ") in >>>>");
		logger.info(dto.getDateCount());
		model.addAttribute("plan", dto);
		
		return "plan/mappage2";
	}
		
	@ResponseBody
	@RequestMapping(value = "detail.do", produces = "application/text; charset=UTF-8")
	public String mappage(HttpServletRequest request, Model model) {
		logger.info("detail.do() in >>>>");
		
		String result = dao.insertPlanDtDo(request, model);
		
		return result;
	}

	
	/*

	
	
	@RequestMapping(value="/insertPlanDt.do", produces = "application/text; charset=UTF8")
	@ResponseBody
	public String insertPlanDtDo(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("insertPlanDtDo in >>>>");

		dao.insertPlanDtDo(model, request);
		
		return "";
	}
	
	
	
	//===== mappage�� form(#frm)���� ���� data insert =====
	@RequestMapping(value="insertMap", produces = "application/text; charset=UTF8")
	@ResponseBody
	public String insertMap(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("insertMap");
		String result = dao.insertMap(model, request);
		
		if(result.equals("success"))
			return "insert-success";
		else
			return "insert-failed";
	}
	*/
	
	
	
}
