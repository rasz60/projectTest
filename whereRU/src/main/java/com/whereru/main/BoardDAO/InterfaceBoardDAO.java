package com.whereru.main.BoardDAO;

import java.util.ArrayList;

import com.whereru.main.BoardDTO.MainDTO;

public interface InterfaceBoardDAO {
	
	
	
	public MainDTO write(MainDTO dto);
	public ArrayList<MainDTO> list();
	public ArrayList<MainDTO> getlist(String boardNum);
	public void deleteBoard(String boardNum);
	public ArrayList<MainDTO> modifyList(String boardNum);
	public void modifyExcute(MainDTO dto);
}
