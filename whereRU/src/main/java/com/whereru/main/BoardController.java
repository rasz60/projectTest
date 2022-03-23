package com.whereru.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.whereru.main.BoardDAO.MainDAO;
import com.whereru.main.BoardDTO.CommentsDTO;
import com.whereru.main.BoardDTO.MainDTO;

@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	
	@Autowired
	private MainDAO dao;
	

	@RequestMapping("/form")
	public String form() {		
		return "form";
	}
	
	@RequestMapping(value = "/uploadMulti", method = { RequestMethod.POST })
	public String uploadMulti(MultipartHttpServletRequest multi, Model model) {
		String images = "";
		String titleImage="";
		String tmp="";
		List<MultipartFile> fileList = multi.getFiles("img");
				
		//집에서 작업할시
		//String path = "C:/Fsunny/whereRU/src/main/webapp/resources/images/";
		
		//학원에서 작업할시
		String path = "C:/Users/310-02/git/projectTest/whereRU/src/main/webapp/resources/images/";
		
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
		images = tmp;
		String[] test =  images.split("/");
		titleImage = test[0];
		
		MainDTO dto = new MainDTO(multi.getParameter("email"),multi.getParameter("title"),multi.getParameter("content"),multi.getParameter("location"),titleImage,images);
		dao.write(dto);
		return "home";
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		ArrayList<MainDTO> list = dao.list();
		model.addAttribute("list", list);
		
		return "list";
	}
	
	@ResponseBody
	@RequestMapping(value="/getlist.do")
	public ArrayList<MainDTO> getlist(@RequestParam("postNo") String postNo) {
		
		ArrayList<MainDTO> dto = dao.getlist(postNo);
		
		return dto;
	}
	
	@RequestMapping("/delete.do")
	public String delete(HttpServletRequest request,Model model) {
		String postNo = request.getParameter("postNo");
		dao.deleteBoard(postNo);
		return "redirect:list";
	}
	
	@RequestMapping("/modify")
	public String modify(HttpServletRequest request,Model model) {
		String postNo = request.getParameter("postNo");
		ArrayList<MainDTO> list =dao.modifyList(postNo);
		
		model.addAttribute("list", list);
		return "modify";
	}
	
	@RequestMapping(value = "/modifyExcute.do", method = { RequestMethod.POST })
	public String modifyExcute(MultipartHttpServletRequest multi, Model model) {
		String images = "";
		String titleImage="";
		String tmp="";
		List<MultipartFile> fileList = multi.getFiles("img");
		
		//집에서 작업할시
		//String path = "C:/Fsunny/whereRU/src/main/webapp/resources/images/";
		
		//학원에서 작업할시
		String path = "C:/Users/310-02/git/projectTest/whereRU/src/main/webapp/resources/images/";
		
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
		images = tmp;
		String[] test =  images.split("/");
		titleImage = test[0];
		
		MainDTO dto = new MainDTO(multi.getParameter("postNo"),multi.getParameter("email"),multi.getParameter("title"),multi.getParameter("content"),multi.getParameter("location"),titleImage,images);
		dao.modifyExcute(dto);
		return "redirect:list";
	}
	
	@ResponseBody
	@RequestMapping("/addcomments.do")
	public void addcomments(@RequestParam("postNo") String postNo, @RequestParam("content") String content){
		CommentsDTO dto = new CommentsDTO(postNo,content);
		dao.addcomments(dto);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/getcomments.do")
	public ArrayList<CommentsDTO> getcomments(@RequestParam("postNo") String postNo) {
		
		ArrayList<CommentsDTO> dto = dao.getcomments(postNo);
		
		return dto;
	}
}
