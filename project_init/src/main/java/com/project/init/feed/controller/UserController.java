package com.project.init.feed.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.project.init.feed.dao.UserDao;
import com.project.init.util.Constant;

@Controller
//@RequestMapping("/user")
public class UserController {
	
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
		return "user/join_view";
	}
	
	@RequestMapping(value = "/user/join", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String join(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("join");
		mcom = new JoinCommand();
		mcom.execute(request, model);
		String result = (String) request.getAttribute("result");
		System.out.println(result);
		if(result.equals("success"))
			return "join-success";
		else
			return "join-failed";
	}
	
	@RequestMapping(value="/user/emailCheck") //�� ����Ʈ ����� �ȵ�?
	@ResponseBody
	public int emailCheck(@RequestParam("id") String id) {
		System.out.println("emailCheck");
		System.out.println(id);
		int res = udao.emailCheck(id);
		System.out.println(res);
		return res;
	}
	
	@RequestMapping(value="/user/nickCheck")
	@ResponseBody
	public int nickCheck(@RequestParam("nick") String nick) {
		System.out.println("nickCheck");
		System.out.println(nick);
		int res = udao.nickCheck(nick);
		System.out.println(res);
		return res;
	}
	
//	@RequestMapping(value="/processLogin", method = RequestMethod.GET)
//	public ModelAndView processLogin(
//			@RequestParam(value = "error", required = false) String error) {
//		System.out.println("processLogin");
//		ModelAndView model = new ModelAndView();
//		if(error != null && error !="" ) { //�α��ν� �����߻��ϸ� security���� ��û(���� 1)
//			model.addObject("error", "���̵�� ��й�ȣ�� �ٽ� Ȯ�����ּ���.");
//			System.out.println(error);
//		}
//		model.setViewName("index");
//		return model;
//	}
	
	@RequestMapping(value="/processLogin")
	public String processLogin(@RequestParam(value="error") String error,Model model) {
		System.out.println("processLogin");
		if(error != null && error !="") {
			model.addAttribute("error", "���̵�� ��й�ȣ�� �ٽ� Ȯ�����ּ���.");
			System.out.println(error);
		}
		return "/main";
	}
	
	//�α��� ������
	@RequestMapping(value="/loginSuc")
	public String loginSuc(Authentication authentication) {
		System.out.println("loginSuc");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Constant.username = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String auth = authorities.toString(); //role�� �� ���ڿ��� ��ȯ
		System.out.println(auth); //[ROLE_USER] ����
		udao.userVisit(Constant.username); //�α��� ��¥ ������Ʈ
		return "/main";
	}
	
	@RequestMapping(value="/user/myPage")
	public String myPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/user/myPage";
	}

}
