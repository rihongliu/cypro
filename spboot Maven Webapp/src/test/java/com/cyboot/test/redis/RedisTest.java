package com.cyboot.test.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.spboot.config.RedisService;

@Controller
public class RedisTest {
	@Autowired
    private RedisService redisService;
	 
	@ResponseBody
	@RequestMapping("/spboot/redistest")
	public Map redisOper(){
		Map map = new HashMap() ;
		map.put("name", "rihongliu") ;
		map.put("age", "23") ;
		map.put("sex", "男") ;
		Map resultMap = new HashMap() ;
		 StringBuffer sb = new StringBuffer();
        redisService.set("str", "str");
        sb.append("str=").append(redisService.get("str").toString()).append(",");
        redisService.hmSet("hmset","key","val");
        sb.append("hmset=").append(redisService.hmGet("hmset","key")).append(",");
        redisService.lPush("list","val");
        sb.append("list=").append(redisService.lRange("list",0,1).toString()).append(",");
        redisService.add("set","val");
        sb.append("set=").append(redisService.setMembers("set").toString()).append(",");
        redisService.zAdd("zset","val1",1);
        redisService.zAdd("zset","val2",2);
        sb.append("zset=").append(redisService.rangeByScore("zset",1,2)).append(",");
        
        redisService.set("test_map", map) ;
        resultMap = (Map)redisService.get("test_map") ;
        resultMap.put("sb", sb) ; 
        return resultMap ;
	}
}
