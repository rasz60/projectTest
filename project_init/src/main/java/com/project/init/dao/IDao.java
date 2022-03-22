package com.project.init.dao;


import com.project.init.dto.PlanMstDto;

public interface IDao {

	void insertPlanMst(PlanMstDto dto);
	
	PlanMstDto selectPlanMst(int planNum);
}
