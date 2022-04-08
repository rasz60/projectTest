package com.project.init.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.command.BoardWriteCommand;
import com.project.init.command.ICommand;
import com.project.init.command.MainMapFilterCommand;
import com.project.init.dao.BoardIDao;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.NoticeBoardDto;
import com.project.init.dto.PlanDtDto;
import com.project.init.util.Constant;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private PlanIDao dao;

	@Autowired
	private BoardIDao bdao;

	private ICommand comm;
	private UserDao udao;
	
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}
	

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
	
	@ResponseBody
	@RequestMapping(value = "/selectPlanList", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDtDto> selectPlanList(Model model) {
		logger.info("selectPlanList() in >>>>");
		
		ArrayList<PlanDtDto> selectPlanList = dao.selectPlanList();
		model.addAttribute("latlng", selectPlanList);
		
		return selectPlanList;
	}

	
	@ResponseBody
	@RequestMapping(value = "/filter", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDtDto> filter(HttpServletRequest request, Model model){
		logger.info("filter() in >>>>");
		comm = new MainMapFilterCommand();
		comm.execute(request, model);
		
		ArrayList<PlanDtDto> filterDtos = (ArrayList)request.getAttribute("filter");

		return filterDtos;
	}
	
	@RequestMapping("/notice_board")
	public String noticeBoard(Model model) {
		logger.info("noticeBoard() in >>>>");
		
		ArrayList<NoticeBoardDto> bdtos = bdao.getBoardList();
		
		model.addAttribute("boardList", bdtos);
		
		logger.info("noticeBoard() result : bdtos.isEmpty() ? " + bdtos.isEmpty());
		
		return "notice_board";
	}
	
	@RequestMapping("/write_view")
	public String writeView(Model model) {
		logger.info("writeView() in >>>>");
		
		model.addAttribute("bName", Constant.username);
		logger.info("write_view result : bName ? " + Constant.username);

		
		return "notice_board_write";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("write in >>>>");
		comm = new BoardWriteCommand();
		comm.execute(request, model);
		
		return "redirect:notice_board";
	}
	
	@RequestMapping("/contentView")
	public String content_view(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("content_view in >>>>");
		String result = "failed";
		

		
		if ( model.containsAttribute("content_view") ) {
			result = "success";
		}
		model.addAttribute("username", Constant.username);
		logger.info("content_view result 1 : " + result);
		logger.info("content_view result 2 : username ? " + Constant.username);
		return "content_view";
	}
	
}
