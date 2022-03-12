package com.whereru.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whereru.main.BoardDAO.MainDAO;

@Controller
public class BoardController {
	
	@Autowired
	private MainDAO dao;
	
	@RequestMapping("/test")
	public String test() {
		dao.test();
		return "test";
	}
}
