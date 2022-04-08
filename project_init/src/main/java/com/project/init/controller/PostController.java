package com.project.init.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.init.command.ICommand;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PlanIDao dao;
	private ICommand comm;
	private UserDao udao;
	
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}
	
	@RequestMapping("posting")
	public String posting(String planNum) {
		logger.info("posting(" + planNum + ") in");
		
		ArrayList<PlanDtDto> dtos = dao.selectPlanDt(planNum, Constant.username);
		
		logger.info("posting("+ planNum +") result.isEmpty() ? " + dtos.isEmpty());
		
		return "feed/addPost";
	}

}
