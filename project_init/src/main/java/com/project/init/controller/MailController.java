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

		String email = request.getParameter("email");
		
		
		// mail subject
        String subject = "[WAYG] ȸ�������� ���� ������ȣ�Դϴ�.";
        
        
        // mail content
        String content= "";
        String pinNum = "";
        for ( int i = 0; i < 6; i++ ) {
        	int pin = (int)(Math.random() * 10);
        	
        	if ( i == 0 && pin == 0 ) {
        		pinNum += pin + 1;
        	}
        	
        	pinNum += pin;
        }
        
        
        content += "�ȳ��ϼ���, WAYG�Դϴ�.<br/>"; 
        content += "�̸��� ������ ���� PIN ��ȣ�Դϴ�.<br/>�Ʒ��� ��ȣ�� Ȯ���Ͻð� ȸ�� ����â�� ��Ȯ�� �Է����ּ���.<br/>";
        content += "<br/>ȸ������ ������ȣ�� " + pinNum + "�Դϴ�.<br />";
        content += "ȸ������ ���������� PIN ��ȣ�� ��Ȯ�� �Է����ּ���.";
        
        
        // sender mail-address
        String from = "WAYG <wayg.superad@gmail.com>";
        
        // receiver mail-address
        String to = email;
        
        
	    try {
	        MimeMessage mail = mailSender.createMimeMessage();
	        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
	            
	        mailHelper.setFrom(from);
	        mailHelper.setTo(to);
	        mailHelper.setSubject(subject);
	        // use html statement
	        mailHelper.setText(content, true);       
	        mailSender.send(mail);
	        return pinNum;
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        return "error";
	    }        
	}
	
	
	@ResponseBody
	@RequestMapping(value = "contactus", method = RequestMethod.GET)
    public String contactus(HttpServletRequest request) throws Exception {
		logger.info("contactus() in >>> ");
		String to ="WAYG <wayg.superad@gmail.com>";
		String from = "WAYG <wayg.superad@gmail.com>";
        String subject = request.getParameter("subject");
        String content = "ȸ�� ���� �ּ� : " + request.getParameter("usermail") + "<br />";
        content += request.getParameter("content");
        
        try {
	        MimeMessage mail = mailSender.createMimeMessage();
	        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
	            
	        mailHelper.setFrom(from);
	        mailHelper.setTo(to);
	        mailHelper.setSubject(subject);
	        // use html statement
	        mailHelper.setText(content, true);       
	        mailSender.send(mail);
	        return "success";
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        return "error";
	    }   
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "findInfo", method = RequestMethod.GET)
    public String findInfo(HttpServletRequest request) throws Exception {
		logger.info("findInfo() in >>> ");
		String to = request.getParameter("to");
		String from = "WAYG <wayg.superad@gmail.com>";
		
		// mail subject
        String subject = "[WAYG] ���̵� ã�� ���� ������ȣ�Դϴ�.";
        
        
        // mail content
        String content= "";
        String pinNum = "";
        for ( int i = 0; i < 6; i++ ) {
        	int pin = (int)(Math.random() * 10);
        	
        	if ( i == 0 && pin == 0 ) {
        		pinNum += pin + 1;
        	}
        	
        	pinNum += pin;
        }

        content += "�ȳ��ϼ���, WAYG�Դϴ�.<br/>"; 
        content += "�̸��� ������ ���� PIN ��ȣ�Դϴ�.<br/>�Ʒ��� ��ȣ�� Ȯ���Ͻð� Pin Ȯ�� â�� ��Ȯ�� �Է����ּ���.<br/>";
        content += "<br/>ȸ������ ������ȣ�� " + pinNum + "�Դϴ�.<br />";
        
	    try {
	        MimeMessage mail = mailSender.createMimeMessage();
	        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
	            
	        mailHelper.setFrom(from);
	        mailHelper.setTo(to);
	        mailHelper.setSubject(subject);
	        // use html statement
	        mailHelper.setText(content, true);       
	        mailSender.send(mail);
	        return pinNum;
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        return "error";
	    }    
		
	}
	
	
	
}
