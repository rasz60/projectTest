package com.project.init.feed.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
@Transactional
public class DaoTests {

	private Logger logger = Logger.getLogger(DaoTests.class);
	
	@Autowired
	private IDao dao;
	@Autowired
	private SqlSession sqlSession;
	@Test
	public void test2_1() {
		
		String startDate = "2022-03-20";
		
		int y = Integer.parseInt(startDate.substring(0, 4));
		int m = Integer.parseInt(startDate.substring(5, 7)) - 1;;
		int d = Integer.parseInt(startDate.substring(8));
		
		Date date = new Date((y-1900), m, d);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 0);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String r = df.format(cal.getTime());
		
		System.out.println(r);
		
	}

	
	@Test
	@Transactional
	public void test3() {
		String[] deleteDtNum = "35/36/37".split("/");
		Map<String ,String> map = new HashMap<>();
		List<Map<String ,String>> deleteDtList = new ArrayList<Map<String ,String>>();

		for ( int i = 0; i < deleteDtNum.length; i++ ) {
			logger.info(deleteDtNum[i]);
			
			map.put("value", "planDtNum");
			map.put("planDtNum", deleteDtNum[i]);
			deleteDtList.add(map);
		}

		logger.info(deleteDtList.get(0).entrySet());

		sqlSession.delete("deleteDt", deleteDtList);
	}
}
