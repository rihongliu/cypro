package com.cy.spboot.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cy.spboot.databean.StationInfo;

public interface StationInfoService {
	public StationInfo findByName(String name);
	
	public List<StationInfo> findByState(String state,Pageable pageable) ;
	
	public List<StationInfo> findByCondition(String state) ;
}
