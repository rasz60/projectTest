package com.jim.sec01.dao;

import java.util.ArrayList;

import com.jim.sec01.dto.JoinDto;
import com.jim.sec01.dto.MessageTO;

public interface MemberIdao {
	//myBatis에서 사용하는 추상메서드들
	// Join
	public String join(JoinDto dto);
	
	//login
	public JoinDto login(String bId);
	
	public ArrayList<MessageTO> messageList(MessageTO to);
	public ArrayList<MessageTO> roomContentList(MessageTO to);
	public int messageSendInlist(MessageTO to);
}
