package com.jim.sec01.memberCommand;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MemberCommand {
	//추상메서드
	public void execute(Model model,HttpServletRequest request);
	//Model에는 결과값을 설정
	//HttpServletRequest는 클라이언트에서 받은 파라메터 처리
}