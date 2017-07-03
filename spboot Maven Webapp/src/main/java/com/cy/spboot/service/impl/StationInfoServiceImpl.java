package com.cy.spboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cy.spboot.dao.StationInfoDao;
import com.cy.spboot.databean.StationInfo;
import com.cy.spboot.service.StationInfoService;

@Service
@Transactional
public class StationInfoServiceImpl implements StationInfoService {
	@Autowired
	private StationInfoDao stationInfoDao;

	@Override
	public StationInfo findByName(String name) {
		return stationInfoDao.findByName(name) ;
	}

	@Override
	public List<StationInfo> findByState(String state,Pageable pageable) {
		return stationInfoDao.findByState(state, pageable) ;
	}
	
	public List<StationInfo> findByCondition(String state){
		return stationInfoDao.findByCondition(state) ;
	}
	
}
