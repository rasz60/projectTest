package com.project.init.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.init.feed.dao.UserDao;
import com.project.init.feed.dao.PlanDAO;

public class Constant {
	
	public static PlanDAO pdao;
	public static BCryptPasswordEncoder passwordEncoder;
	public static UserDao udao;
	public static String username;
}
