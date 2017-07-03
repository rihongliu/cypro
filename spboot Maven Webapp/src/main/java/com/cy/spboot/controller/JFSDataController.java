package com.cy.spboot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class JFSDataController {
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 
//	 @Cacheable(value="jsfData_cache")
	 @ResponseBody
     @RequestMapping("/spboot/getJSFData")
     public String jsfData(String content){
		 
		 String param = "" ;
		 content  = "{\"type\":\"temperature\",\"lon\":116.9,\"lat\":38.9,\"layer\":\"2m\"}" ;
		 ObjectMapper mapper = new ObjectMapper();  
		 Map paraMap = new HashMap() ;
		 try {
			paraMap =  mapper.readValue(content, Map.class) ;
			Object resultType = paraMap.get("resultType") ;
			if(resultType == null || "".equals(resultType)){
				paraMap.put("resultType", "data") ;	//设置resultMap的默认值是data，resultType：image，代表base64字符串
			}
			param = mapper.writeValueAsString(paraMap) ;
			System.out.println(param);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 String m = "" ;
		 final CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		 Message ms = rabbitTemplate.getMessageConverter().toMessage(param.getBytes(), new MessageProperties()) ;
	     ms.getMessageProperties().setCorrelationId(correlationId.toString().getBytes()) ;
	     try {
			 Message mg = rabbitTemplate.sendAndReceive("", "GFS_MODEL_SERVICE", ms,correlationId) ;
			 if(new String(mg.getMessageProperties().getCorrelationId()).equals(correlationId.toString())){
				 m = new String(mg.getBody()) ;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	     System.out.println(m);
	     return m ;
	 }
}
