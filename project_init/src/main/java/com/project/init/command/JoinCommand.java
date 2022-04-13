package com.project.init.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import com.project.init.dao.UserDao;
import com.project.init.util.Constant;

public class JoinCommand implements ICommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		BCryptPasswordEncoder passwordEncoder = Constant.passwordEncoder;
		UserDao udao = Constant.udao;
		
		String UEmail = request.getParameter("uEmail");
		String UPw = request.getParameter("uPw1");
		String UNickName = request.getParameter("uNickName");
		String UBirth = request.getParameter("uBirth");
		//String UAge = getAgeByBirthDay(UBirth);
		String UGender = request.getParameter("uGender");
		String UPst = request.getParameter("uPst");
		String UAddr1 = request.getParameter("uAddr1");
		String UAddr2 = request.getParameter("uAddr2");

		
		String UPw_org = UPw; //��ȣȭ �Ǳ��� password�� UPw_org�� ����
		UPw = passwordEncoder.encode(UPw_org); //��ȣȭ
		System.out.println(UPw + " size " + UPw.length());
		
		String result = udao.join(UEmail,UPw,UNickName,UBirth,UGender,UPst, UAddr1, UAddr2);
		
		request.setAttribute("result", result); //controller���� ��� ���
	}
	
}