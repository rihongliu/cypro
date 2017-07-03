package com.cy.spboot.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;

import com.cy.spboot.databean.StationInfo;

public class StationInfoDaoImpl{
	
	@PersistenceContext
	private EntityManager em ;
	
	@Cacheable(value="station_cache")
	public List<StationInfo> findByCondition(String state){
		Query q = em.createNativeQuery("select * from station_info s where s.state = ?0",StationInfo.class).setParameter(0, "1") ;
		return q.getResultList() ;
	}
}
