package com.cyboot.test.mqrpc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

@Controller
//@Scope("singleton")
public class JFSDataController {
	
	private String msg ;
	
	
	private final static String SEND_QUEUE_NAME = "modelservice1";
	
	private final static String RECV_QUEUE_NAME = "modelservice2";
	private static String message = "error" ;
	
	public static void Recv(){
		System.out.println("reve");
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("124.17.31.233");
		Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(RECV_QUEUE_NAME, false, false, false, null); 
			
			//创建队列消费者  
	        QueueingConsumer consumer = new QueueingConsumer(channel); 
	        //指定消费队列  
	        channel.basicConsume(RECV_QUEUE_NAME, true, consumer); 
	        
	        while (true)  
	        {  
	            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
	            QueueingConsumer.Delivery delivery;
				try {
					delivery = consumer.nextDelivery();
					message = new String(delivery.getBody()); 
//					System.out.println(message);
				} catch (ShutdownSignalException e) {
					e.printStackTrace();
				} catch (ConsumerCancelledException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
	            
	        }  
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		 
		
	}
	@ResponseBody
	@RequestMapping("/spboot/getgfsData")
	public String send(@RequestParam(value="lon",required=true, defaultValue="116.8")double lon,
			@RequestParam(value="lat",required=true, defaultValue="38.9")double lat,
			@RequestParam(value="type",required=false, defaultValue="temperature")String type){
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("124.17.31.233");
		Connection connection;
		try {
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();
			channel.queueDeclare(SEND_QUEUE_NAME, false, false, false, null);
			Map map = new HashMap();
			map.put("lon", lon);
			map.put("lat", lat);
			map.put("type", type) ;
			final JSONObject  jsonObj  = JSONObject.fromObject(map);
			for(int x = 0 ; x < 10 ;x++){
				final String content = "xxxxxx" + x; 
				new Thread(new Runnable(){
					public void run(){
						try {
							
							channel.basicPublish("", SEND_QUEUE_NAME, null, content.getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start() ;
			}
			channel.confirmSelect();
			try {
				System.out.println(channel.waitForConfirms());
				System.out.println("-=--------------------------mseggg = " + message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		
		return message ;
	}
	
	public static void main(String[] args) {
		Recv() ;
	}
}
