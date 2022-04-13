package com.project.init.controller;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mail")
public class MailController {

	private static final Logger logger = LoggerFactory.getLogger(MailController.class);
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@ResponseBody
	@RequestMapping(value = "joinCert", method = RequestMethod.GET)
    public String joinCert(HttpServletRequest request) throws Exception {
		logger.info("joinCert() in >>> ");
		// ajax로 고객이 입력한 메일주소를 보내서 Controller로 인증번호를 보낸다.
		String email = request.getParameter("email");
		
		
		// mail 제목
        String subject = "[WAYG] 회원가입을 위한 인증번호입니다.";
        
        
        // mail 본문 내용
        String content= "";
        String pinNum = "";
        for ( int i = 0; i < 6; i++ ) {
        	int pin = (int)(Math.random() * 10);
        	
        	if ( i == 0 && pin == 0 ) {
        		pinNum += pin + 1;
        	}
        	
        	pinNum += pin;
        }
        
        
        content += "안녕하세요, WAYG입니다.<br/>"; 
        content += "이메일 인증을 위한 PIN 번호입니다.<br/> 아래의 번호를 확인하시고 회원 가입창에 정확히 입력해주세요.<br/><br/>";
        content += "<hr/>";
        content += "<br/>회원님의 인증번호는 " + pinNum + "입니다.<br/>";
        content += "회원가입 페이지에서 PIN 번호를 정확히 입력해주세요.";
        
        
        // 보내는 사람
        String from = "WAYG <wayg.superad@gmail.com>";
        
        // 받는 사람
        String to = email;
        
        
	    try {
	        MimeMessage mail = mailSender.createMimeMessage();
	        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
	            
	        mailHelper.setFrom(from);
	        mailHelper.setTo(to);
	        mailHelper.setSubject(subject);
	        mailHelper.setText(content, true);
	        // html태그를 사용하려면 true           
	        mailSender.send(mail);
	        return pinNum;
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        return "error";
	    }        
	}
	
}
