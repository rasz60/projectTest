package com.project.init.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.init.dao.PostIDao;
import com.project.init.dto.PostDtDto;
import com.project.init.dto.PostDto;
import com.project.init.util.Constant;

public class PostModifyDoCommand implements ICommand {

	private PostIDao postDao = Constant.postDao;
	
	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)request;
		
		String titleImage="";
		String tmp="";
		String images = multi.getParameter("images");
		System.out.println(images);
		
		List<MultipartFile> fileList = multi.getFiles("img");
		
		
		//String path = "C:/Users/310-08/git/projectTest/project_init/src/main/webapp/resources/images/";
		String path = "F:/init/init_project/projectTest/project_init/src/main/webapp/resources/images/";
		
		for (MultipartFile mf : fileList) {
			String originalFileName = mf.getOriginalFilename();
			UUID prefix = UUID.randomUUID();
			
			try {
				mf.transferTo(new File(path + prefix + originalFileName));
				tmp+=prefix + originalFileName+"/";
			} catch (IllegalStateException | IOException e) {
				e.getMessage(); 
			}
		}
		images += tmp;
		String[] test =  images.split("/");
		titleImage = test[0];
		
		PostDto dto = new PostDto(multi.getParameter("postNo"),
								  Constant.username,
				  				  multi.getParameter("content"),
				  				  multi.getParameter("hashtag"),
				  				  titleImage,
				  				  images);
		
		ArrayList<PostDtDto> insertDtDtos = new ArrayList<PostDtDto>();
		ArrayList<PostDtDto> deleteDtDtos = new ArrayList<PostDtDto>();
		
		//postDt modify
		if ( request.getParameterValues("planDtNum") != null ) {
			String[] planDtNum = request.getParameterValues("planDtNum");
			String[] placeName = request.getParameterValues("placeName");
			
			int planNum = Integer.parseInt(multi.getParameter("planNum"));

			for(int i = 0; i < planDtNum.length; i++ ) {
				PostDtDto dtDto = new PostDtDto(Integer.parseInt(dto.getPostNo()),
												planNum,
												Integer.parseInt(planDtNum[i]),
												placeName[i]);
				
				insertDtDtos.add(dtDto);
			}
		}
		
		if ( multi.getParameter("delDtNum").equals("false") == false ) {
			String[] delDtNum = multi.getParameter("delDtNum").split("/");
			
			for(int i = 0; i < delDtNum.length; i++ ) {
				PostDtDto dtDto = new PostDtDto();
				dtDto.setPostNo(Integer.parseInt(dto.getPostNo()));
				dtDto.setPlanDtNum(Integer.parseInt(delDtNum[i]));
				deleteDtDtos.add(dtDto);
			}
		}
		postDao.modifyExcute(dto, insertDtDtos, deleteDtDtos);

	}

}
