package com.project.init.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.init.dto.NoticeBoardDto;

public class BoardModifyCommand implements ICommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		String bId = request.getParameter("bId");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		NoticeBoardDto dto = new NoticeBoardDto();
		dto.setbId(Integer.parseInt(bId));
		dto.setbTitle(bTitle);
		dto.setbContent(bContent);
		
		
	}

}
