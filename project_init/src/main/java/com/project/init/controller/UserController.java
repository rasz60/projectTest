package com.project.init.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.command.ICommand;
import com.project.init.command.JoinCommand;
import com.project.init.dao.UserDao;
import com.project.init.util.Constant;

@Controller
//@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserDao udao;
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}
	
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Constant.passwordEncoder = passwordEncoder;
	}
	
	private ICommand mcom;
	
	@RequestMapping("/user/join_view")
	public String join() {
		logger.info("join_view() in >>> ");
		return "join/join";
	}
	
	@RequestMapping(value = "/user/join", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String join(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("join() in >>> ");
		
		mcom = new JoinCommand();
		mcom.execute(request, model);
		
		String result = (String) request.getAttribute("result");
		System.out.println(result);
		
		logger.info("join() result : " + result);
		
		if(result.equals("success"))
			return "join-success";
		else
			return "join-failed";
	}
	
	@RequestMapping(value="/user/emailCheck")  //왜 포스트 방식은 안돼?
	@ResponseBody
	public int emailCheck(@RequestParam("id") String id) {
		logger.info("emailCheck(" + id + ") in >>> ");
		
		int res = udao.emailCheck(id);
		
		logger.info("emailCheck(" + id + ") result : " + (res > 0 ? "success": "false"));
		return res;
	}
	
	@RequestMapping(value="/user/nickCheck")
	@ResponseBody
	public int nickCheck(@RequestParam("nick") String nick) {
		logger.info("nickCheck(" + nick + ") in >>> ");
		
		int res = udao.nickCheck(nick);
		
		logger.info("emailCheck(" + nick + ") result : " + (res > 0 ? "success": "false"));
		return res;
	}
	

	@RequestMapping(value="/processLogin")
	public String processLogin(@RequestParam(value="error", required = false) String error, 
							   @RequestParam(value="logout", required = false) String logout, Model model) {
		
		logger.info("processLogin() in >>> ");
		
		if(error != null && error !="") {
			model.addAttribute("error", "아이디와 비밀번호를 다시 확인해주세요.");
			
			logger.info("processLogin() result : error");
			return "/index";
		}
		
		if(logout != null && logout != "") {
			Constant.username = "";
			
			logger.info("processLogin() result : logout");
			return "redirect:/";
		}
		
		
		return "redirect:/";
	}
	
	//로그인 성공시
	@RequestMapping(value="/loginSuc")
	public String loginSuc(Authentication authentication) {
		logger.info("loginSuc() in >>> ");
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Constant.username = userDetails.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String auth = authorities.toString(); //role을 얻어서 문자열로 변환

		udao.userVisit(Constant.username); //로그인 날짜 업데이트
		
		logger.info("loginSuc() userAuth : " + auth);
		
		return "redirect:/";
	}
	
}