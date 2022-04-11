package com.project.init.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.project.init.command.PlanMstModifyCommand;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dto.UserDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private PlanIDao dao;
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
		logger.info("feed page " + Constant.username + " >>>>");

		return "feed/feed_calendar";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanMstDto> getAllPlans() {
		logger.info("getAllPlans() >>>>");
		// ������ ���̵�� ��ϵ� ������ ��� ������
		ArrayList<PlanMstDto> result = dao.selectAllPlan(Constant.username);
				
		logger.info("getAllPlans() result.isEmpty() ? " + result.isEmpty());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "modify_modal.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDtDto> modifyModal(@RequestBody String planNum, Model model) {
		logger.info("modifyModal("+ planNum +") in >>>>");

		ArrayList<PlanDtDto> result= dao.selectPlanDt(planNum, Constant.username);
		
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
		
		String result = dao.deletePlanMst(planNum, Constant.username);
		
		return result;
	}
	
	@RequestMapping("feedMap.do")
	public String feedMapDo() {
		logger.info("feedMap.do() in >>>>");
		return "redirect:/feed/feedMap";
	}
	
	@RequestMapping("feedMap")
	public String feedMap() {
		logger.info("feedMap() in >>>>");
		return "feed/feed_map";
	}
	
	
	@RequestMapping("feedPost.do")
	public String feedPostDo() {
		logger.info("feedPost.do() in >>>>");
		return "redirect:/post/mypost";
	}

	@RequestMapping("feedInfo.do")
	public String feedInfoDo(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("feedInfo.do() in >>>>");
		
		comm = new MypageCommand();
		comm.execute(request, model);
		
		logger.info("feedInfo out");
		
		return "redirect:/feed/feedInfo";
	}
	
	@RequestMapping("feedInfo")
	public String feedInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("feedInfo() in >>>>");
		
		comm = new MypageCommand();
		comm.execute(request, model);
		
		logger.info("feedInfo out");
		
		return "feed/feed_user_info";
	}
	
	
	
	
	//�����ʻ��� ���
	@RequestMapping("add_PrfImg")
	public String add_PrfImg(MultipartHttpServletRequest mtpRequest, HttpServletRequest request, Model model) {
		System.out.println("add_PrfImg");
		
		String olduPrfImg = udao.getolduPrfImg(Constant.username); //�̹� DB�� ������ִ� �̹������� �̸� ��������
		String uPrfImg = null; //DB����� ���ϸ�
		
		MultipartFile mf = mtpRequest.getFile("pImg");
		
		String path = "D:/project_init/project_init/src/main/webapp/resources/profileImg/";
		String path1 = "D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/resources/profileImg/";
		String originFileName = mf.getOriginalFilename();
		
		long prename = System.currentTimeMillis();
		long fileSize = mf.getSize();
		
		System.out.println("originFileName : " + originFileName);
		System.out.println("fileSize : " + fileSize);
		
		String safeFile = path + prename + originFileName;
		String safeFile1 = path1 + prename + originFileName;
		
		uPrfImg = prename + originFileName;
		
		UserDto udto = new UserDto(Constant.username,null,null,null,0,null,0,null,uPrfImg,null,null,null,null,null,null,null);
		mtpRequest.setAttribute("udto", udto);
		
		comm = new AddPrfImgCommand();
		
		comm.execute(mtpRequest, model);
		
		Map<String, Object> map = model.asMap();
		String res = (String) map.get("result");
		
		if(res.equals("success")) {
			try {
				mf.transferTo(new File(safeFile));
				mf.transferTo(new File(safeFile1));
				
				//���� ������ִ� ���� ����
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
			return "redirect:/feed/feedInfo.do";
		}
		else {
			return "redirect:/feed/feedInfo.do";
		}
	}
	
	@RequestMapping("eraseImg")
	public String eraseImg() {
		System.out.println("eraseImg");
		String olduPrfImg = udao.getolduPrfImg(Constant.username);
		udao.deletePrfImg(Constant.username);
		
		//���� ������ִ� ���� ����
		String path = "C:/ecl/workspaceWEB/WAYGprj/src/main/webapp/resources/profileImg/";
		String path1 = "C:/ecl/apache-tomcat-9.0.56/wtpwebapps/WAYGprj/resources/profileImg/";
		File file = new File(path + olduPrfImg);
		File file1 = new File(path1 + olduPrfImg);
		if(file.exists()) {
			file.delete();
		}
		if(file1.exists()) {
			file1.delete();
		}
		
		return "redirect:/feed/feedInfo.do";
	}
	
	//���������� ����
	@RequestMapping("modifyMyPage")
	@ResponseBody
	public String modifyMyPage(@RequestParam(value="userNick") String userNick, @RequestParam(value="userBio") String userProfileMsg, @RequestParam(value="userPst") String userPst, @RequestParam(value="userAddr1") String userAddress1, @RequestParam(value="userAddr2") String userAddress2, HttpServletRequest request, Model model) {
		System.out.println("modifyMyPage");
		int UserPst = Integer.parseInt(userPst);
		UserDto udto = new UserDto(Constant.username, null, userNick, null, 0, null, UserPst, userAddress1, null, userProfileMsg, null, null, null, null, null, userAddress2);
		request.setAttribute("udto", udto);
		comm = new MdfMyPageCommand();
		comm.execute(request, model);
		String result = (String) request.getAttribute("result");
		System.out.println(result);
		if(result.equals("success"))
			return "modified";
		else
			return "not-modified";
	}
	
	//��й�ȣ ���� �� ��й�ȣ Ȯ��
	@RequestMapping(value="chkPwForMdf", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String chkPwForMdf(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("chkPwForMdf");
		String Crpw = request.getParameter("crpw");
		String upw = udao.pwcheck(Constant.username);
		passwordEncoder = new BCryptPasswordEncoder();
		if(passwordEncoder.matches(Crpw, upw)) {
			return "Correct-pw";
		} else {
			return "Incorrect-pw";
		}
			
	}
	
	//��й�ȣ ����
	@RequestMapping(value="modifyPw", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String modifyPw(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("modifyPw");
			comm = new ModifyPwCommand();
			comm.execute(request, model);
			String result = (String) request.getAttribute("result");
			System.out.println(result);
			if(result.equals("success"))
				return "pw-modified";
			else
				return "pw-not-modified";
	}
	
	//ȸ��Ż��� ��й�ȣ Ȯ��
	@RequestMapping(value="chkPwForResig", method=RequestMethod.POST, produces = "application/text; charset=UTF8")
	@ResponseBody
	public String chkPwForResig(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("chkPwForResig");
		String RgPw = request.getParameter("rgPw");
		String upw = udao.pwcheck(Constant.username);
		passwordEncoder = new BCryptPasswordEncoder();
		if(passwordEncoder.matches(RgPw, upw)) {
			return "Correct-pw";
		} else {
			return "Incorrect-pw";
		}
		
	}
	
	//ȸ��Ż��
	@RequestMapping(value="resignation")
	public String resignation() {
		System.out.println("resignation");
		udao.resign(Constant.username);
		SecurityContextHolder.clearContext(); //ȸ��Ż��� �α׾ƿ� ����
		return "redirect:/";
	}

}
