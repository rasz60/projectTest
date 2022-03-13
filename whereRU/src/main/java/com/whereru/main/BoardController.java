package com.whereru.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.whereru.main.BoardDAO.MainDAO;

@Controller
public class BoardController {
	
	@Autowired
	private MainDAO dao;
	
	@RequestMapping("/uploadMulti")
	public String uploadMulti(MultipartHttpServletRequest multi, Model model) {
		List<MultipartFile> fileList = multi.getFiles("img");

		String nickname = multi.getParameter("nickname");
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");

		System.out.println(nickname);
		System.out.println(title);
		System.out.println(content);
		
		String src = multi.getParameter("src");
		
		String path1 = "C:/Fsunny/whereRU/src/main/webapp/resources/images/";
		
		for (MultipartFile mf : fileList) {
			String originalFileName = mf.getOriginalFilename();
			System.out.println(originalFileName);
			//long prefix = System.currentTimeMillis();
			UUID prefix = UUID.randomUUID();
			
			try {
				mf.transferTo(new File(path1 + prefix + originalFileName));
				System.out.println(path1 + prefix + originalFileName);
			} catch (IllegalStateException | IOException e) {
				e.getMessage();
			}
			System.out.println(originalFileName);
		}
		
		return "home";
	}
	
}
