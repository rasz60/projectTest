package com.project.init.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.init.dao.SearchDao;
import com.project.init.dto.PostDto;
import com.project.init.dto.SearchDto;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchDao searchDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@RequestMapping("search")
	public String search(HttpServletRequest request, Model model) {
		logger.info("search() in >>>>");

		String keyword = request.getParameter("keyword");
		String searchVal = request.getParameter("searchVal");
		SearchDto dto = new SearchDto(keyword, searchVal);
		
		ArrayList<PostDto> list = searchDao.search(dto);
		
		if(list.size()==0) {
			return "error/nullPage";			
		}
		model.addAttribute("list", list);
		
		return "search/searchResult";
	}
	
}
