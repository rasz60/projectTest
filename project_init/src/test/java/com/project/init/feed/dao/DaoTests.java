package com.project.init.feed.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.project.init.feed.dto.PlanDto;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
public class DaoTests {

	private Logger logger = Logger.getLogger(DaoTests.class);
	@Autowired
	private IDao dao;
	
	
	@Test
	public void test1() {
		ArrayList<PlanDto> list = dao.selectAllPlan();
		
		for(PlanDto i : list) {
			logger.info(i);
		}
	}
}
