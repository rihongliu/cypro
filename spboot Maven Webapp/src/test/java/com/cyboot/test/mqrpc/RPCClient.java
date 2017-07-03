package com.cyboot.test.mqrpc;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RPCClient {  
    private Connection connection;  
    private Channel channel;  
    private String requestQueueName = "compute_queue";  
    private String replyQueueName;  
    private QueueingConsumer consumer;  
  
    public RPCClient() throws Exception {  
        //• 先建立一个连接和一个通道，并为回调声明一个唯一的'回调'队列  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("124.17.31.233");  
        factory.setUsername("guest");  
        factory.setPassword("guest");  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        connection = factory.newConnection();  
        channel = connection.createChannel();  
        //• 注册'回调'队列，这样就可以收到RPC响应  
        replyQueueName = channel.queueDeclare().getQueue();  
        consumer = new QueueingConsumer(channel);  
        channel.basicConsume(replyQueueName, true, consumer);  
        
//        System.out.println(replyQueueName);
    }  
  
    //发送RPC请求  
    public String call(String message) throws Exception {  
        String response = null;  
        String corrId = java.util.UUID.randomUUID().toString();  
        //发送请求消息，消息使用了两个属性：replyto和correlationId  
        BasicProperties props = new BasicProperties.Builder()  
                .correlationId(corrId).replyTo(replyQueueName).build();  
        channel.basicPublish("", requestQueueName, props, message.getBytes());  
        //等待接收结果
        int i = 0 ;
        while (true) {  
        	QueueingConsumer.Delivery delivery = consumer.nextDelivery(10);
            //QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            
            //检查它的correlationId是否是我们所要找的那个  
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {  
                response = new String(delivery.getBody());  
                break;  
            }

        }  
        return response;  
    }  
    public void close() throws Exception {  
        connection.close();  
    }  
    
    
    public static void main(String[] args) throws Exception {  
        for(int i = 0 ; i < 10 ; i++){
        	final String content = String.valueOf(i) ;
        	new Thread(new Runnable(){
    			public void run(){
    				try {
    					 final RPCClient rpcClient = new RPCClient();  
    					  String response = rpcClient.call(content);  
    				      System.out.println("RESPONES = === = " + response);  
    				      rpcClient.close();  
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}).start() ;
        }
    }  
    
} 