package com.project.init.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.init.command.ICommand;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.PostIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dto.PostDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PlanIDao pdao;
	@Autowired
	private PostIDao postDao;
	private ICommand comm;
	private UserDao udao;
	
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}

	
	/* unset
	
	@RequestMapping("postMain")
	public String postMain(Model model) {
		String user = Constant.username;
		ArrayList<PostDto> list = dao.list(user);
		model.addAttribute("list", list);
		model.addAttribute("user",Constant.username);
		System.out.println(Constant.username);
		return "post/postMain";
	}
	
	@RequestMapping("postLike")
	public String postLike(Model model) {
		String user = Constant.username;
		ArrayList<PostDto> list = dao.likeList(user);
		model.addAttribute("list", list);
		model.addAttribute("user",Constant.username);
		System.out.println(Constant.username);
		return "post/postMain";
	}
	
	@RequestMapping("postView")
	public String postView(Model model) {
		String user = Constant.username;
		ArrayList<PostDto> list = dao.viewList(user);
		model.addAttribute("list", list);
		model.addAttribute("user",Constant.username);
		System.out.println(Constant.username);
		return "post/postMain";
	}
	
	@RequestMapping("getPost")
	public String getPost(Model model) {
		String user = Constant.username;
		ArrayList<PostDto> list = dao.list(user);
		model.addAttribute("list", list);
		model.addAttribute("user",Constant.username);
		System.out.println(Constant.username);
		return"post/postMain";
	}
	
	
	*/
	
	
	@RequestMapping("posting")
	public String posting(String planNum, Model model) {
		logger.info("posting(" + planNum + ") in");
		
		// planNum이 일치하는 planMst 정보를 가져와서 model에 담음
		PlanMstDto result1= pdao.selectPlanMst(planNum, Constant.username);
		model.addAttribute("plan1", result1);
		
		logger.info("posting("+ planNum +") result1.isEmpty() ? " + result1.getDateCount());
		
		
		// planNum이 일치하는 planDt 정보를 가져와서 model에 담음
		ArrayList<PlanDtDto> result2= pdao.selectPlanDt(planNum, Constant.username);
		model.addAttribute("plan2", result2);
		
		logger.info("posting("+ planNum +") result2.isEmpty() ? " + result2.isEmpty());
		
		return "feed/addPost";
	}
	
	
	@RequestMapping(value = "uploadMulti", method = { RequestMethod.POST })
	public String uploadMulti(MultipartHttpServletRequest multi, Model model) {
		String images = "";
		String titleImage="";
		String tmp="";
		int views =0;
		List<MultipartFile> fileList = multi.getFiles("img");
		String path = "F:/init/init_project/projectTest/project_init/src/main/webapp/resources/images/";
		for (MultipartFile mf : fileList) {
			String originalFileName = mf.getOriginalFilename();
			UUID prefix = UUID.randomUUID();
			
			try {
				mf.transferTo(new File(path + prefix + originalFileName));
				tmp+=prefix + originalFileName+"/";
			
			} catch (IllegalStateException | IOException e) {
				e.getMessage(); 
				System.out.println(e.getMessage());
			}
		}
		
		images = tmp;
		String[] test = images.split("/");
		titleImage = test[0];
		PostDto dto = new PostDto(multi.getParameter("email"),
								  multi.getParameter("content"),
								  multi.getParameter("hashtag"),
								  multi.getParameter("planDtNum"),
								  titleImage,
								  images,
								  views);
		
		postDao.write(dto);
		
		return "redirect:/feed";
	}
	
	
	/*
	
	@ResponseBody
	@RequestMapping("getlist.do")
	public ArrayList<PostDto> getlist(@RequestParam("postNo") String postNo,@RequestParam("email") String email) {
		PostDto tmp = new PostDto(postNo,email);
		ArrayList<PostDto> dto = dao.getlist(tmp);
		System.out.println("dddddddd");
		return dto;
	}
	
	@RequestMapping("delete.do")
	public String delete(HttpServletRequest request,Model model) {
		String postNo = request.getParameter("postNo");
		dao.deleteBoard(postNo);
		return "redirect:list";
	}
	
	@RequestMapping("modify")
	public String modify(HttpServletRequest request,Model model) {
		String postNo = request.getParameter("postNo");
		ArrayList<PostDto> list =dao.modifyList(postNo);
		
		model.addAttribute("list", list);
		return "modify";
	}
	
	@RequestMapping(value = "modifyExcute.do", method = { RequestMethod.POST })
	public String modifyExcute(MultipartHttpServletRequest multi, Model model) {
		String images = "";
		String titleImage="";
		String tmp="";

		
		List<MultipartFile> fileList = multi.getFiles("img");
		
		
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
		images = tmp;
		String[] test =  images.split("/");
		titleImage = test[0];
		
		PostDto dto = new PostDto(multi.getParameter("postNo"),multi.getParameter("content"),multi.getParameter("hashtag"),multi.getParameter("location"),titleImage,images);
		dao.modifyExcute(dto);
		return "redirect:list";
	}
	
	@ResponseBody
	@RequestMapping("addcomments.do")
	public void addcomments(@RequestParam("postNo") String postNo, @RequestParam("content") String content,@RequestParam("grpl") String grpl,@RequestParam("email") String email){
		int Igrpl = Integer.parseInt(grpl);
		CommentsDto dto = new CommentsDto(postNo,content,Igrpl,email);
		dao.addcomments(dto);
		
	}
	
	@ResponseBody
	@RequestMapping("addReplyComments.do")
	public void addReplyComments(@RequestParam("postNo") String postNo,@RequestParam("grp") String grp, @RequestParam("content") String content,@RequestParam("grpl") String grpl,@RequestParam("grps") String grps,@RequestParam("email") String email){
		int Igrp = Integer.parseInt(grp);
		int Igrpl = Integer.parseInt(grpl);
		int Igrps = Integer.parseInt(grps);
		CommentsDto dto = new CommentsDto(postNo,Igrp,content,Igrpl,Igrps,email);
		dao.addReplyComments(dto);
		
	}
	
	@ResponseBody
	@RequestMapping("deleteReplyComments.do")
	public void deleteReplyComments(@RequestParam("commentNo") String commentNo){
		dao.deleteReplyComments(commentNo);		
	}
	
	@ResponseBody
	@RequestMapping(value="getcomments.do")
	public ArrayList<CommentsDto> getcomments(@RequestParam("postNo") String postNo) {
		
		ArrayList<CommentsDto> dto = dao.getcomments(postNo);
		
		return dto;
	}
	
	@RequestMapping("searchPage")
	public String searchPage(HttpServletRequest request, Model model) {
		String keyword = request.getParameter("keyword");
		String searchVal = request.getParameter("searchVal");
		ArrayList<PostDto> list = dao.search(keyword,searchVal);
		model.addAttribute("list", list);
		
		return "postMain";
	}

	@ResponseBody
	@RequestMapping(value="addLike.do",produces = "application/text; charset=UTF-8")
	public String addLike(@RequestParam("postNo") String postNo,@RequestParam("email") String email) {
		
		PostLikeDto dto = new PostLikeDto(postNo, email);
		String result = dao.addLike(dto);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("addView.do")
	public String addView(@RequestParam("postNo") String postNo,@RequestParam("email") String email) {
		PostViewDto dto = new PostViewDto(postNo, email);
		String result = dao.addView(dto);
		return result;
		
	}
	 
	*/

}
