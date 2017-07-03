package com.cyboot.test.mqrpc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@ResponseBody
	@RequestMapping("/spboot/showUser")
	public List showUser(){
		User user = new User() ;
		user.setName("张三") ;
		user.setAge(12l) ;
		user.setSex("男") ;
		
		User user1 = new User() ;
		user1.setName("李四") ;
		user1.setAge(32l) ;
		user1.setSex("女") ;
		
		List list = new ArrayList() ;
		list.add(user1) ;
		list.add(user1) ;
		return list ;
		
	}
}
