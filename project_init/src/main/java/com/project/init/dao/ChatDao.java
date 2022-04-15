package com.project.init.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.dto.ChatMessageDto;
import com.project.init.dto.ChatRoomDto;
import com.project.init.dto.UserDto;
import com.project.init.util.Constant;

@Component
public class ChatDao implements ChatIDao {
	@Autowired
	private SqlSession sqlSession;
	
	
	public ChatDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		Constant.cdao = this;
	}
	
	@Override
	public String idFromNick(String subNick) {
		String subId = sqlSession.selectOne("idFromNick",subNick);
		return subId;
	}
	
	@Override
	public String nickFromId(String pubId) {
		String pubNick =sqlSession.selectOne("nickFromId",pubId);
		return pubNick;
	}
	
	@Override
	public int checkChatRoom(ChatRoomDto chkroom) {
		int num = sqlSession.selectOne("checkChatRoom",chkroom);
		return num;
	}
	
	@Override
	public void createChatRoom(ChatRoomDto crdto) {
		sqlSession.insert("createChatRoom",crdto);
	}

	@Override
	public ArrayList<ChatRoomDto> chatRoomList(String id) {
		ArrayList<ChatRoomDto> result = (ArrayList)sqlSession.selectList("chatRoomList",id);
		return result;
	}

	@Override
	public ChatRoomDto getChatRoomDto(String roomId) {
		ChatRoomDto result = sqlSession.selectOne("getChatRoomDto",roomId);
		return result;
	}

	@Override
	public ArrayList<ChatMessageDto> getChatMessageDto(String roomId) {
		ArrayList<ChatMessageDto> result = (ArrayList)sqlSession.selectList("getChatMessageDto",roomId);
		return result;
	}

	@Override
	public void saveMsg(ChatMessageDto message) {
		sqlSession.insert("saveMsg",message);
	}
	
	@Override
	public String updatePImg(UserDto udto) {
		int res = sqlSession.update("profileImgUpdate", udto);
		
		return res == 1 ? "success" : "failed";
	}
	
	@Override
	   public String findUserImg(String uId) {
	      String userImg = sqlSession.selectOne("findUserImg",uId);
	      return userImg;
	   }
	
	@Override
	   public void mdfChatRoomSImg(Map<String, Object> map) {
	      sqlSession.update("mdfChatRoomSImg",map);
	   }

	   @Override
	   public void mdfChatRoomPImg(Map<String, Object> map) {
	      sqlSession.update("mdfChatRoomPImg",map);
	   }

}