package com.project.init.dao;

import java.util.ArrayList;
import java.util.Map;

import com.project.init.dto.ChatMessageDto;
import com.project.init.dto.ChatRoomDto;
import com.project.init.dto.UserDto;

public interface ChatIDao {
	public String idFromNick(String subNick);
	
	public String nickFromId(String pubId);

	public int checkChatRoom(ChatRoomDto chkroom);
	
	public void createChatRoom(ChatRoomDto crdto);
	
	public ArrayList<ChatRoomDto> chatRoomList(String id);
	
	public ChatRoomDto getChatRoomDto(String roomId);

	public ArrayList<ChatMessageDto> getChatMessageDto(String roomId);
	
	public void saveMsg(ChatMessageDto message);
	
	public String updatePImg(UserDto udto);
	
	public String findUserImg(String uId);
	
	public void mdfChatRoomSImg(Map<String, Object> map);

    public void mdfChatRoomPImg(Map<String, Object> map);
}