package com.project.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.project.init.dao.PlanDao;
import com.project.init.dto.PlanMstDto;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
@Transactional
public class InitTests {

	@Autowired
	public PlanDao pdao;
	
	@Test
	public void test1 () {
		PlanMstDto dto = new PlanMstDto(0, "test", "1231", "1233", "123124");		
		pdao.insertPlanMst(dto);
	}
}
