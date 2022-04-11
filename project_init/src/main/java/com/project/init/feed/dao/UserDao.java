package com.project.init.feed.dao;

import java.util.Calendar;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.feed.dto.UserDto;

@Component
public class UserDao implements UserIDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int emailCheck(String id) {
		int result = sqlSession.selectOne("emailCheck",id); 
		return result;
	}
	
	@Override
	public int nickCheck(String nick) {
		int result = sqlSession.selectOne("nickCheck",nick);
		return result;
	}
	
	@Override
	public String join(String UEmail, String UPw, String UNickName, String UBirth, String UGender, String UPst, String UAddr) {
		int UAgeNum = getAgeByBirthDay(UBirth);
		int UPstNum = Integer.parseInt(UPst);
		UserDto dto = new UserDto(UEmail,UPw,UNickName,UBirth,UAgeNum,UGender,UPstNum,UAddr,null,null,null,null,null,null,null, null, null);
		int res = sqlSession.insert("join",dto);
		System.out.println(res);
		String result = null;
		if(res > 0)
			result = "success";
		else
			result = "failed";
		
		return result;
	}
	
	//�깮�뀈�썡�씪濡� 留뚮굹�씠 援ы븯湲�
	private int getAgeByBirthDay(String UBirth) {
		//�뀈,�썡,�씪 �옄瑜닿린
		int bir_year = Integer.parseInt(UBirth.substring(0,4));
		int bir_month = Integer.parseInt(UBirth.substring(4,6));
		int bir_day = Integer.parseInt(UBirth.substring(6));
		//�쁽�옱�뀈,�썡,�씪 get	
		Calendar current = Calendar.getInstance();
		int cur_year = current.get(Calendar.YEAR);
		int cur_month = current.get(Calendar.MONTH);
		int cur_day = current.get(Calendar.DAY_OF_MONTH);
		int age = cur_year - bir_year;
		//留뚮굹�씠
		if(bir_month*100+bir_day>cur_month*100+cur_day) {
			age--;
		}
		return age;
		
	}

	@Override
	public UserDto login(String uId) {
		System.out.println(uId);
		UserDto result = sqlSession.selectOne("login",uId);
		return result;
	}

	@Override
	public void userVisit(String uId) {
		sqlSession.insert("userVisit",uId);
	}

}
