package com.project.init.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.dto.NoticeBoardDto;
import com.project.init.util.Constant;

@Component
public class BoardDao implements BoardIDao {
	private static final Logger logger = LoggerFactory.getLogger(PlanDao.class);
	
	private final SqlSession sqlSession;

	// sqlSession ������ ����
	@Autowired
	public BoardDao (SqlSession sqlSession) {
		logger.info("PlanDao Const in >>>");
		this.sqlSession = sqlSession;
		
		Constant.bdao = this;
		logger.info("PlanDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}
	
	// �������� �Խ��� ����Ʈ �ҷ�����
	@Override
	public ArrayList<NoticeBoardDto> getBoardList() {
		logger.info("getBoardList() in >>>>");
		
		ArrayList<NoticeBoardDto> result = (ArrayList)sqlSession.selectList("listNotice");
		
		logger.info("list() result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}

	// �������� �Խ��� �� �ۼ�
	@Override
	public void write(String bName, String bTitle, String bContent) {
		logger.info("write() in >>>>");
		
		NoticeBoardDto dto = new NoticeBoardDto();
		dto.setbName(bName);
		dto.setbTitle(bTitle);
		dto.setbContent(bContent);
		
		int res = sqlSession.insert("writeNotice", dto);
		logger.info("write() result : " + (res > 0 ? "success" : "failed"));
	}
	
	// �������� �Խ��� �� ���� ������ ����
	public NoticeBoardDto contentView(String bid) {
		logger.info("contentView(" + bid + ") in >>>>");
		
		upHit(bid);
		
		NoticeBoardDto dto = sqlSession.selectOne("contentViewNotice", Integer.parseInt(bid));
		
		logger.info("contentView result : " + dto.getbName());
		
		return dto;
	}
	
	// ��ȸ�� �÷��ִ� �޼���
	public void upHit(String bId) {
		logger.info("upHit(" + bId + ") in >>> ");	
		
		int res = sqlSession.update("upHitNotice", Integer.parseInt(bId));
		
		logger.info("upHit result : " + res);
	}
	
	
}
