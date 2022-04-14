package com.project.init.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.init.dao.PostIDao;
import com.project.init.dao.SearchDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PostDto;
import com.project.init.dto.SearchDto;
import com.project.init.dto.UserDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchDao searchDao;
	
	@Autowired
	private UserDao udao;
	
	@Autowired
	private PostIDao postDao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@RequestMapping("search")
	public String search(HttpServletRequest request, Model model) {
		logger.info("search() in >>>>");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		UserDto udto = udao.login(uId);
		model.addAttribute("user", udto);
		
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
	
	
	@RequestMapping("lastest")
	public String lastest(HttpServletRequest request, Model model) {
		logger.info("search() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDto dto = Constant.getUserInfo(authentication);
		
		
		ArrayList<PostDto> post = postDao.list(dto.getUserEmail());
		
		if(post.size()==0) {
			return "error/nullPage";			
		}
		
		
		model.addAttribute("list", post);
		
		System.out.println(post.get(0).getLikes());
		
		return "search/searchResult";
	}
	
	@RequestMapping("bestLikes")
	public String bestLikes(HttpServletRequest request, Model model) {
		logger.info("search() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDto dto = Constant.getUserInfo(authentication);

		ArrayList<PostDto> likeList = postDao.likeList(dto.getUserEmail());
		
		if(likeList.size()==0) {
			return "error/nullPage";			
		}
		
		model.addAttribute("list", likeList);
		
		return "search/searchResult";
	}

	@RequestMapping("bestViews")
	public String bestViews(HttpServletRequest request, Model model) {
		logger.info("search() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDto dto = Constant.getUserInfo(authentication);
		
		
		ArrayList<PostDto> viewList = postDao.viewList(dto.getUserEmail());
		
		if(viewList.size()==0) {
			return "error/nullPage";			
		}

		model.addAttribute("list", viewList);
		
		return "search/searchResult";
	}
	
}
