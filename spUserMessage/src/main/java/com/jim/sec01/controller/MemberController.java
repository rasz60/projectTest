package com.jim.sec01.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jim.sec01.dao.MemberDao;
import com.jim.sec01.memberCommand.JoinCommand;
import com.jim.sec01.memberCommand.MemberCommand;
import com.jim.sec01.util.Constant;

@Controller
public class MemberController {
	//전역변수 설정
	private MemberCommand mcom; //command의 인터페이스 객체 선언
	
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Constant.passwordEncoder = passwordEncoder;
	}
	
	private MemberDao mdao;
	@Autowired
	public void setMdao(MemberDao mdao) {
		this.mdao = mdao;
		Constant.mdao = mdao;
	}
	
	//회원가입부
	@RequestMapping("/join_view")
	public String join_view() {
		return "join_view";
	}
	
	@RequestMapping("/login_view")
	public String login_view()	{
		return "login_view";
	}
	
	@RequestMapping(value="/join", produces = "application/text; charset=UTF8")
	//produces는 미디어 처리로 ajax로 요청시 한글 처리
	@ResponseBody //jsp가 아닌 문자열이나 객체를 보내기 때문에 필요
	public String join(HttpServletRequest request,HttpServletResponse response, Model model) {
		System.out.println("join");
		mcom = new JoinCommand();
		mcom.execute(model, request);
		String result = (String)request.getAttribute("result");
		System.out.println(result);
		if(result.equals("success"))
			return "join-success";
		else
			return "join_failed";
	}
	
	//processLogin요청은 security-context.xml에서 로그인 실패시 /processLogin?error=1,
	//로그아웃시 /processLogin?logout=1로 호출하거나 form이 아닌 <a href="processLogin?log=1">으로
	//호출시에 사용
	//@RequestParam(value = "log", required = false)는 request파라메터값을 변수에 사용
	//false이면 사용안해도 됨
	@RequestMapping(value = "/processLogin", method = RequestMethod.GET)
	public ModelAndView processLogin(
			@RequestParam(value = "log", required = false) String log,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpSession session
			) {
		ModelAndView model = new ModelAndView();
		if (log != null && log !="")
			model.addObject("log", "before login!");
		if (error != null && error !="")
			model.addObject("error", "Invalid username or password!");
		if (logout != null && logout !="")
			model.addObject("logout", "You've been logged out successfully.");
		
		model.setViewName("login_view"); //jsp설정
		return model;
	}
	
	@RequestMapping("/main")
	public String main()	{
		return "message_list";
	}
	
}