package com.cy.spboot.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cy.spboot.databean.StationInfo;
@Repository
public interface StationInfoDao  extends JpaRepository<StationInfo, Long>,JpaSpecificationExecutor<StationInfo> {
	StationInfo findByName(String name);
	
	 @Query("from StationInfo t where pkId = :id")
	 List<StationInfo> queryFamilyList(@Param("id") Long id);
	 
	 List<StationInfo> findByState(String state,Pageable pageable) ;
	 
	 public List<StationInfo> findByCondition(String state) ;
}
