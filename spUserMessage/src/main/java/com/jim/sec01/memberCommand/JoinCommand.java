package com.jim.sec01.memberCommand;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import com.jim.sec01.dao.MemberDao;
import com.jim.sec01.dto.JoinDto;
import com.jim.sec01.util.Constant;

public class JoinCommand implements MemberCommand {

	@Override
	public void execute(Model model, HttpServletRequest request) {
		//��ȣȭ ��ü
		BCryptPasswordEncoder passwordEncoder = Constant.passwordEncoder;
		
		String bId = request.getParameter("pid");
		String bPw = request.getParameter("ppw");
		String baddress = request.getParameter("paddress");
		String bhobby = request.getParameter("phobby");
		String bprofile = request.getParameter("pprofile");
		
		String bPw_org = bPw; //bPw_org�� ��ȣȭ �� pw
		bPw = passwordEncoder.encode(bPw_org); //bPw�� ��ȣȭ�� pw
		System.out.println(bPw + " size " + bPw.length());
		
		JoinDto dto = new JoinDto(bId,bPw,baddress,bhobby,bprofile);
		
		MemberDao mdao = Constant.mdao;
		
		String result = mdao.join(dto);
		
		request.setAttribute("result", result);
	}

}
