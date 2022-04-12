package com.project.init.command;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.init.dao.PlanIDao;
import com.project.init.dao.PostIDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dto.PostDto;
import com.project.init.util.Constant;

public class PostModifyCommand implements ICommand {

	private PostIDao postDao = Constant.postDao;
	private PlanIDao planDao = Constant.pdao;
	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		String postNo = request.getParameter("postNo");
		
		// result 1 : PostDto
		PostDto dto = new PostDto();
		dto.setPostNo(postNo);
		dto.setEmail(Constant.username);
		
		dto = postDao.getlist(dto);
		model.addAttribute("postDto", dto);
		
		// result 2 : planMstDto
		PlanMstDto pmst = planDao.selectPlanMst(dto.getPlan(), Constant.username);
		model.addAttribute("planMstDto", pmst);
		
		// result 3 : planDtDto
		ArrayList<PlanDtDto> pdts = planDao.selectPlanDt(dto.getPlan(), Constant.username);
		model.addAttribute("planDtDtos", pdts);
	}

}
