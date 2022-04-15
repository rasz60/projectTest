package com.project.init.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.init.command.AddPrfImgCommand;
import com.project.init.command.ICommand;
import com.project.init.command.MdfMyPageCommand;
import com.project.init.command.ModifyPwCommand;
import com.project.init.command.MypageCommand;
import com.project.init.command.PlanDtGetMapCommand;
import com.project.init.command.PlanMstModifyCommand;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.PostIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dto.PostDto;
import com.project.init.dto.UserDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private PlanIDao dao;
	@Autowired
	private PostIDao postDao;
	private ICommand comm;
	private UserDao udao;
	
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}
	
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Constant.passwordEncoder = passwordEncoder;
	}
	
	
	@RequestMapping("")
	public String feed(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		UserDto dto = udao.login(uId);
		int planCount = dao.countPlanMst(uId);
		int postCount = postDao.countPost(uId);
		
		model.addAttribute("user", dto);
		model.addAttribute("planCount", planCount);
		model.addAttribute("postCount", postCount);
		logger.info("feed page " + uId + " >>>>");

		return "feed/feed_calendar";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanMstDto> getAllPlans() {
		logger.info("getAllPlans() >>>>");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		ArrayList<PlanMstDto> result = dao.selectAllPlan(uId);
				
		logger.info("getAllPlans() result.isEmpty() ? " + result.isEmpty());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "modify_modal.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDtDto> modifyModal(@RequestBody String planNum, Model model) {
		logger.info("modifyModal("+ planNum +") in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		ArrayList<PlanDtDto> result= dao.selectPlanDt(planNum, uId);
		
		logger.info("modifyModal("+ planNum +") result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "modify_plan.do", produces="application/text; charset=UTF-8")
	public String modifyPlan(HttpServletRequest request) {
		logger.info("modifyPlan("+ request.getParameter("planNum") +") in >>>>");

		String result= null;
		
		comm = new PlanMstModifyCommand();
		comm.execute(request, null);
		
		result = (String)request.getAttribute("result");
		
		logger.info("modifyPlan("+ request.getParameter("planNum") +") result : " + result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete_plan.do", produces="application/text; charset=UTF-8")
	public String deleteMstPlan(@RequestBody String planNum) {
		logger.info("deletePlans("+ planNum +") in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		String result = dao.deletePlanMst(planNum, uId);
		
		return result;
	}
	
	
	@RequestMapping("feedMap")
	public String feedMap(Model model) {
		logger.info("feedMap() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		UserDto dto = udao.login(uId);
		int planCount = dao.countPlanMst(uId);
		int postCount = postDao.countPost(uId);
		
		model.addAttribute("user", dto);
		model.addAttribute("planCount", planCount);
		model.addAttribute("postCount", postCount);
		
		return "feed/feed_map";
	}

	@ResponseBody
	@RequestMapping(value="getAllPlansMap.do", produces="application/json; charset=UTF-8")
	public ArrayList<Object> getAllPlansMap(HttpServletRequest request, Model model) {
		logger.info("getAllPlansMap() >>>>");

		comm = new PlanDtGetMapCommand();
		comm.execute(request, model);
		
		ArrayList<Object> result = new ArrayList<Object>();
		result.add((ArrayList)request.getAttribute("getMapPost"));
		result.add((ArrayList)request.getAttribute("selectPlanDtMap"));
		
		
		logger.info("getAllPlansMap() result : result.size() ? " + result.size());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getMapPost.do", produces="application/json; charset=UTF-8")
	public PostDto getMapPost(HttpServletRequest request, Model model) {
		logger.info("getMapPost() >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		PostDto dto = new PostDto(request.getParameter("postNo"), uId);
		dto = postDao.getlist(dto);
		
		logger.info("getMapPost() result : dto ? " + dto.getPostNo());
		return dto;
	}
	
	
	
	@RequestMapping("feedInfo")
	public String feedInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("feedInfo() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		
		comm = new MypageCommand();
		comm.execute(request, model);
		int planCount = dao.countPlanMst(uId);
		int postCount = postDao.countPost(uId);
		
		model.addAttribute("planCount", planCount);
		model.addAttribute("postCount", postCount);

		logger.info("feedInfo out");
		
		return "feed/feed_user_info";
	}
	
	
	// 프로필 이미지 변경
	@RequestMapping("add_PrfImg")
	public String add_PrfImg(MultipartHttpServletRequest mtpRequest, HttpServletRequest request, Model model) {
		logger.info("add_PrfImg() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		
		String olduPrfImg = udao.getolduPrfImg(uId);
		String uPrfImg = null;
		
		MultipartFile mf = mtpRequest.getFile("pImg");
		
		String path = "C:/Users/310-08/git/projectTest/project_init/src/main/webapp/resources/profileImg/";
		String path1 = "C:/Users/310-08/git/projectTest/apache-tomcat-9.0.56/wtpwebapps/project_init/resources/profileImg/";
		
		//String path = "F:/init/init_project/projectTest/project_init/src/main/webapp/resources/profileImg/";
		//String path1 = "F:/init/init_project/projectTest/apache-tomcat-9.0.56/wtpwebapps/project_init/resources/profileImg/";
		String originFileName = mf.getOriginalFilename();
		
		long prename = System.currentTimeMillis();
		long fileSize = mf.getSize();

		logger.info("add_PrfImg() result1 - originFileName : " + originFileName + ", fileSize : " + fileSize);

		
		String safeFile = path + prename + originFileName;
		String safeFile1 = path1 + prename + originFileName;
		
		uPrfImg = prename + originFileName;
		
		UserDto udto = new UserDto(uId,null,null,null,0,null,0,null,uPrfImg,null,null,null,null,null,null,null);
		mtpRequest.setAttribute("udto", udto);
		
		comm = new AddPrfImgCommand();
		comm.execute(mtpRequest, model);
		
		Map<String, Object> map = model.asMap();
		String res = (String) map.get("result");
		
		if(res.equals("success")) {
			try {
				mf.transferTo(new File(safeFile));
				mf.transferTo(new File(safeFile1));
				
				File file = new File(path + olduPrfImg);
				File file1 = new File(path1 + olduPrfImg);
				if(file.exists()) {
					file.delete();
				}
				if(file1.exists()) {
					file1.delete();
				}
			}
			catch(Exception e) {
				e.getMessage();
			}
			return "redirect:/feed/feedInfo";
		}
		else {
			return "redirect:/feed/feedInfo";
		}
	}
	
	@RequestMapping("eraseImg")
	public String eraseImg() {
		logger.info("eraseImg() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		String olduPrfImg = udao.getolduPrfImg(uId);
		
		udao.deletePrfImg(uId);

		//String path = "C:/Users/310-08/git/projectTest/project_init/src/main/webapp/resources/profileImg/";
		//String path1 = "C:/Users/310-08/git/projectTest/apache-tomcat-9.0.56/wtpwebapps/project_init/resources/profileImg/";
		
		String path = "F:/init/init_project/projectTest/project_init/src/main/webapp/resources/profileImg/";
		String path1 = "F:/init/init_project/projectTest/apache-tomcat-9.0.56/wtpwebapps/project_init/resources/profileImg/";
		
		File file = new File(path + olduPrfImg);
		File file1 = new File(path1 + olduPrfImg);
		if(file.exists()) {
			file.delete();
		}
		if(file1.exists()) {
			file1.delete();
		}
		
		return "redirect:/feed/feedInfo";
	}
	
	
	@RequestMapping("modifyMyPage")
	@ResponseBody
	public String modifyMyPage(@RequestParam(value="userNick") String userNick, 
							   @RequestParam(value="userBio") String userProfileMsg, 
							   @RequestParam(value="userPst") String userPst, 
							   @RequestParam(value="userAddr1") String userAddress1, 
							   @RequestParam(value="userAddr2") String userAddress2, 
							   HttpServletRequest request, Model model) {
		
		logger.info("modifyMyPage() in >>>>");
		int UserPst = Integer.parseInt(userPst);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		
		UserDto udto = new UserDto(uId, null, userNick, null, 0, null, UserPst, userAddress1, null, userProfileMsg, null, null, null, null, null, userAddress2);
		
		request.setAttribute("udto", udto);
		comm = new MdfMyPageCommand();
		comm.execute(request, model);
		
		String result = (String) request.getAttribute("result");
		
		
		logger.info("modifyMyPage() result : " + result);
		
		if(result.equals("success"))
			return "modified";
		else
			return "not-modified";
	}
	
	//占쏙옙橘占싫� 占쏙옙占쏙옙 占쏙옙 占쏙옙橘占싫� 확占쏙옙
	@RequestMapping(value="chkPwForMdf", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String chkPwForMdf(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("chkPwForMdf() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		String result = null;
		
		String Crpw = request.getParameter("crpw");
		String upw = udao.pwcheck(uId);
		
		passwordEncoder = new BCryptPasswordEncoder();
		
		if(passwordEncoder.matches(Crpw, upw)) {
			result = "Correct-pw";
		} else {
			result = "Incorrect-pw";
		}
		
		logger.info("chkPwForMdf() result : " + result);
		
		return result;
	}
	
	
	@RequestMapping(value="modifyPw", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String modifyPw(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("modifyPw() in >>>>");
		
		comm = new ModifyPwCommand();
		comm.execute(request, model);
		
		String result = (String) request.getAttribute("result");
		
		logger.info("modifyPw() result : " + result);
		
		if(result.equals("success"))
			return "pw-modified";
		else
			return "pw-not-modified";
	}
	
	
	@RequestMapping(value="chkPwForResig", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String chkPwForResig(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("chkPwForResig() in >>>>");
		
		String result = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		String RgPw = request.getParameter("rgPw");
		String upw = udao.pwcheck(uId);
		
		passwordEncoder = new BCryptPasswordEncoder();
		
		if(passwordEncoder.matches(RgPw, upw)) {
			result = "Correct-pw";
		} else {
			result = "Incorrect-pw";
		}
		
		logger.info("chkPwForResig() result : " + result);
		
		return result;
	}
	
	
	@RequestMapping(value="resignation")
	public String resignation() {
		logger.info("resignation() in >>>>");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		
		udao.resign(uId);
		SecurityContextHolder.clearContext();
		
		return "redirect:/";
	}
	
	@RequestMapping("otherUser")
	public String otherUserFeed(HttpServletRequest request, Model model) {
		String nickName = request.getParameter("nick");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		UserDto dto = udao.login(uId);
		model.addAttribute("my", dto);
		
		System.out.println(dto.getUserNick());
		
		if ( nickName.equals(dto.getUserNick()) ) {
			return "redirect:/feed";
		}
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nick", nickName);
		map.put("uId", uId);
		
		UserDto otherUser = udao.searchNick(map);
		int planCount = dao.countPlanMst(otherUser.getUserEmail());
		int postCount = postDao.countPost(otherUser.getUserEmail());
		
		model.addAttribute("user", otherUser);
		model.addAttribute("planCount", planCount);
		model.addAttribute("postCount", postCount);
		
		return "feed/other_feed_map";
	}
	
	

}
