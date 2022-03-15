package com.jim.sec01.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jim.sec01.dao.MemberDao;
import com.jim.sec01.dto.MessageTO;
import com.jim.sec01.util.Constant;


/**
 * Handles requests for the application home page.
 */
@Controller
public class MessageController {

	@Autowired
	private MemberDao mdao;

	// �޼��� ���
	@RequestMapping(value = "/message_list.do")
	public String message_list(HttpServletRequest request, HttpServletResponse response) {
	//public String message_list(HttpServletRequest request, HttpSession session) {
		// System.out.println("���� ����� nick : " + session.getAttribute("nick"));

		String nick = Constant.username;

		MessageTO to = new MessageTO();
		to.setNick(nick);

		// �޼��� ����Ʈ
		ArrayList<MessageTO> list = mdao.messageList(to);

		request.setAttribute("list", list);

		return "message_list";
	}

	// �޼��� ���
	@RequestMapping(value = "/message_ajax_list.do")
	public String message_ajax_list(HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("���� ����� nick : " + session.getAttribute("nick"));

		String nick = Constant.username;

		MessageTO to = new MessageTO();
		to.setNick(nick);
		
		// �޼��� ����Ʈ
		ArrayList<MessageTO> list = mdao.messageList(to);

		request.setAttribute("list", list);
		
		return "message_ajax_list";
	}

	@RequestMapping(value = "/message_content_list.do")
	public String message_content_list(HttpServletRequest request, HttpServletResponse response) {

		int room = Integer.parseInt(request.getParameter("room"));
		//����ȯ �ٽ� ��

		MessageTO to = new MessageTO();
		to.setRoom(room);
		to.setNick(Constant.username);

		// �޼��� ������ �����´�.
		ArrayList<MessageTO> clist = mdao.roomContentList(to);

		request.setAttribute("clist", clist);

		return "message_content_list";
	}

	// �޼��� ����Ʈ���� �޼��� ������
	@ResponseBody
	@RequestMapping(value = "/message_send_inlist.do")
	public int message_send_inlist(@RequestParam int room, @RequestParam String other_nick,
			@RequestParam String content, HttpServletResponse response) {

		MessageTO to = new MessageTO();
		to.setRoom(room);
		to.setSend_nick(Constant.username);
		to.setRecv_nick(other_nick);
		to.setContent(content);

		int flag = mdao.messageSendInlist(to);

		return flag;
	}

}
