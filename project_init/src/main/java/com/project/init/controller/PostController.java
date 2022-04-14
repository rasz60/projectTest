package com.project.init.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.init.command.ICommand;
import com.project.init.command.PostModifyCommand;
import com.project.init.command.PostModifyDoCommand;
import com.project.init.command.PostWriteCommand;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.PostIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.CommentsDto;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dto.PostDto;
import com.project.init.dto.PostLikeDto;
import com.project.init.dto.PostViewDto;
import com.project.init.dto.UserDto;
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


	@RequestMapping("posting")
	public String posting(String planNum, Model model) {
		logger.info("posting(" + planNum + ") in >>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		
		PlanMstDto result1= pdao.selectPlanMst(planNum, uId);
		model.addAttribute("plan1", result1);
		
		logger.info("posting("+ planNum +") result1.isEmpty() ? " + result1.getDateCount());
		
		ArrayList<PlanDtDto> result2= pdao.selectPlanDt(planNum, uId);
		model.addAttribute("plan2", result2);
		
		logger.info("posting("+ planNum +") result2.isEmpty() ? " + result2.isEmpty());
		
		model.addAttribute("user", uId);
		
		return "post/addPost";
	}
	
	
	@RequestMapping(value = "uploadMulti", method = { RequestMethod.POST })
	public String uploadMulti(MultipartHttpServletRequest multi, Model model) {
		logger.info("uploadMulti() in >>>");		
		
		comm = new PostWriteCommand();
		
		comm.execute(multi, model);
		
		logger.info("uploadMulti result : " + multi.getAttribute("result").toString());
		
		return "redirect:/feed";
	}
	
	@RequestMapping("mypost")
	public String mypost(Model model) {
		logger.info("mypost() in >>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		UserDto dto = udao.login(uId);
		
		int planCount = pdao.countPlanMst(uId);
		int postCount = postDao.countPost(uId);
		
		model.addAttribute("planCount", planCount);
		model.addAttribute("postCount", postCount);		

		ArrayList<PostDto> list = postDao.mylist(uId, model);
		
		model.addAttribute("list", list);
		model.addAttribute("user", dto);
		
		logger.info("mypost() result : post.isEmpty ? " + list.isEmpty());
		
		return "post/mypost";
	}
	
	@ResponseBody
	@RequestMapping("addView.do")
	public String addView(@RequestParam("postNo") String postNo,@RequestParam("email") String email) {
		logger.info("addView(" + postNo + ") in >>>");
		
		PostViewDto dto = new PostViewDto(postNo, email);
		String result = postDao.addView(dto);
		
		logger.info("addView(" + postNo + ") result : " + result);
		
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping(value="getcomments.do")
	public ArrayList<CommentsDto> getcomments(@RequestParam("postNo") String postNo) {
		logger.info("getcomments(" + postNo + ") in >>>");
		
		ArrayList<CommentsDto> dto = postDao.getcomments(postNo);
		
		logger.info("getcomments(" + dto + ") result : dto.isEmpty() ? " + dto.isEmpty());
		
		return dto;
	}
	
	
	@ResponseBody
	@RequestMapping("addReplyComments.do")
	public void addReplyComments(@RequestParam("postNo") String postNo,@RequestParam("grp") String grp, @RequestParam("content") String content,@RequestParam("grpl") String grpl,@RequestParam("grps") String grps,@RequestParam("email") String email){
		logger.info("addReplyComments(" + postNo + ") in >>>");
		
		int Igrp = Integer.parseInt(grp);
		int Igrpl = Integer.parseInt(grpl);
		int Igrps = Integer.parseInt(grps);
		CommentsDto dto = new CommentsDto(postNo, Igrp, content, Igrpl, Igrps, email);
		postDao.addReplyComments(dto);

	}
	
	@ResponseBody
	@RequestMapping("deleteReplyComments.do")
	public void deleteReplyComments(@RequestParam("commentNo") String commentNo){
		logger.info("deleteReplyComments(" + commentNo + ") in >>>");
		
		postDao.deleteReplyComments(commentNo);		
	}
	
	
	@ResponseBody
	@RequestMapping("addcomments.do")
	public void addcomments(@RequestParam("postNo") String postNo, @RequestParam("content") String content,@RequestParam("grpl") String grpl,@RequestParam("email") String email){
		logger.info("addcomments(" + postNo + ") in >>>");
		
		int Igrpl = Integer.parseInt(grpl);
		CommentsDto dto = new CommentsDto(postNo,content,Igrpl,email);
		postDao.addcomments(dto);
		
	}
	
	@ResponseBody
	@RequestMapping(value="addLike.do",produces = "application/text; charset=UTF-8")
	public String addLike(@RequestParam("postNo") String postNo, @RequestParam("email") String email) {
		logger.info("addLike(" + postNo + ") in >>>");
		
		PostLikeDto dto = new PostLikeDto(postNo, email);
		String result = postDao.addLike(dto);
		
		logger.info("addLike(" + postNo + ") result : " + result);
		
		return result;
	}

	@ResponseBody
	@RequestMapping("getlist.do")
	public PostDto getlist(@RequestParam("postNo") String postNo, @RequestParam("email") String email, Model model) {
		logger.info("addLike(" + postNo + ") in >>>");
		
		PostDto tmp = new PostDto(postNo, email);
		tmp = postDao.getlist(tmp);
		
		logger.info("addLike(" + postNo + ") result : tmp.getPostNo() ?" + tmp.getPostNo());
		
		return tmp;
	}
	
	@RequestMapping("modify")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("modify(" + request.getParameter("postNo") + ") in >>>");
		
		comm = new PostModifyCommand();
		comm.execute(request, model);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		model.addAttribute("user", uId);
		
		return "post/modifypost";
	}
	
	@RequestMapping(value = "modifyExcute.do", method = { RequestMethod.POST })
	public String modifyExcute(MultipartHttpServletRequest multi, Model model) {
		System.out.println(multi.getParameter("delDtNum"));
		
		
		comm = new PostModifyDoCommand();
		comm.execute(multi, model);
		return "redirect:/post/mypost";
	}

	@RequestMapping("delete.do")
	public String delete(HttpServletRequest request,Model model) {
		String postNo = request.getParameter("postNo");
		postDao.deletePost(postNo);
		return "redirect:/post/mypost";
	}
	
	@RequestMapping("otherUser")
	public String otherPost(HttpServletRequest request, Model model) {
		String nickName = request.getParameter("nick");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		UserDto dto = udao.login(uId);
		model.addAttribute("myinfo", dto);
		System.out.println(dto.getUserNick());
		
		if ( nickName.equals(dto.getUserNick()) ) {
			return "redirect:/mypost";
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nick", nickName);
		map.put("uId", uId);
		
		UserDto otherUser = udao.searchNick(map);
		int planCount = pdao.countPlanMst(otherUser.getUserEmail());
		int postCount = postDao.countPost(otherUser.getUserEmail());
		
		model.addAttribute("planCount", planCount);
		model.addAttribute("postCount", postCount);
		
		UserDto dto2 = udao.login(otherUser.getUserEmail());
		
		ArrayList<PostDto> list = postDao.mylist(otherUser.getUserEmail(), model);
		
		model.addAttribute("list", list);
		model.addAttribute("user", dto2);
		
		
		
		logger.info("mypost() result : post.isEmpty ? " + list.isEmpty());
		
		return "post/otherPost";
	}
	
	
}
