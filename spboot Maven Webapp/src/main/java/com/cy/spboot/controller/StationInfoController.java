package com.cy.spboot.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.spboot.config.RedisService;
import com.cy.spboot.databean.StationInfo;
import com.cy.spboot.service.StationInfoService;

@Controller
public class StationInfoController {
	
	@Autowired
	private StationInfoService stationInfoService;
	
	@Autowired
	public RedisService redisService ;
	
	/**
	 * http://localhost:8090/spboot/getStation?name=永和
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/spboot/getStation")
	public StationInfo findStation(String name){
		return stationInfoService.findByName(name) ;
	}
	
	@ResponseBody
	@RequestMapping("/spboot/getState")
	public List<StationInfo> findByState(String state,
			@RequestParam(value="page",required=false, defaultValue="1") int page,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize){
		Order idOrder = new Order(Direction.DESC, "pkId");  
        Order nameOrder = new Order(Direction.DESC,"state");  
        Sort sort = new Sort(idOrder, nameOrder);  
		PageRequest pr = new PageRequest(page,pageSize,sort) ;
		
		return stationInfoService.findByState(state, pr) ;
	}
	
	@ResponseBody
	@RequestMapping("/spboot/getCondition")
	public List<StationInfo> findByCondition(String state){
		return stationInfoService.findByCondition(state) ;
	}
	
	@ResponseBody
	@RequestMapping("/spboot/rcont")
	public String getRedisCondition(){
		//测试数据是否进入到redis中。。。。。。。。。。。。
		List list = new ArrayList() ;
		Collection list1 = (Collection)redisService.get("com.cy.spboot.dao.impl.StationInfoDaoImplfindByCondition1") ;
		
		System.out.println(list1.size());
		
		return "23" ;
	}
	
	@ResponseBody
	@RequestMapping("/spboot/sessionPut")
	public String putSession(HttpSession session,String key,String value){
		session.setAttribute(key, value) ;
		return key + "===>" + value ;
	}
	
	@ResponseBody
	@RequestMapping("/spboot/sessionGet")
	public String getSession(HttpSession session,String key){
		String value = (String)session.getAttribute(key) ;
		return key + "===>" + value ;
	}
	
}
