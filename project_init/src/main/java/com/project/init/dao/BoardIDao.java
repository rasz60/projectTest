package com.project.init.dao;

import java.util.ArrayList;

import com.project.init.dto.NoticeBoardDto;

public interface BoardIDao {
	
	ArrayList<NoticeBoardDto> getBoardList();
	
	public void write(String bName, String bTitle, String bContent);
	
	NoticeBoardDto contentView(String bid);

	void noticeModify(NoticeBoardDto dto);
	
	void noticeDelete(int bId);
}
