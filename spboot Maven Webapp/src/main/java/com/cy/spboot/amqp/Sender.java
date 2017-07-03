package com.cy.spboot.amqp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


//@Controller
public class Sender {
	private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    
    private static String msg ;
    
    private final static String SEND_QUEUE_NAME = "modelservice1";
    private final static String RECV_QUEUE_NAME = "modelservice2";

    @Autowired
    public Sender(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ResponseBody
    @RequestMapping("/spboot/getTemp")
    public void sendTest(Object content){
    	final Message mes = null ;
        final Map map = new HashMap() ;
        final List list = new ArrayList() ;
    	for(int i = 0 ; i < 10; i++){
    		final Object x = "id = " + i ;
    		final CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
			/*final MessagePostProcessor ppm = new MessagePostProcessor(){
	            @Override
	            public Message postProcessMessage(Message message) throws AmqpException {
	            	 message.getMessageProperties().setReceivedRoutingKey(correlationId.getId()) ;
	            	 message.getMessageProperties().setCorrelationId(correlationId.toString().getBytes()) ;
	                return message;
  			  String m = new String((byte[])rabbitTemplate.convertSendAndReceive(
					"", "compute_queue", "",ppm,correlationId)) ;

	            }} ;*/
	        Message ms = rabbitTemplate.getMessageConverter().toMessage(x, new MessageProperties()) ;
	        ms.getMessageProperties().setCorrelationId(correlationId.toString().getBytes()) ;
			list.add(correlationId) ;
			String m = "" ;
			Message mg = rabbitTemplate.sendAndReceive("", "compute_queue", ms,correlationId) ;
			if(new String(mg.getMessageProperties().getCorrelationId()).equals(correlationId.toString())){
				m = new String(mg.getBody()) ;
				map.put(correlationId, m) ;
			}
		}
    	for(int i = 0 ; i < list.size() ; i++){
    		System.out.println(list.get(i) + "======" + (String)map.get(list.get(i)));
    	}
    		/*new Thread(new Runnable(){
    			public void run(){
    				final CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
    				final MessagePostProcessor ppm = new MessagePostProcessor(){
    		            @Override
    		            public Message postProcessMessage(Message message) throws AmqpException {
    		            	 message.getMessageProperties().setReceivedRoutingKey(correlationId.getId()) ;
    		            	 message.getMessageProperties().setCorrelationId(correlationId.toString().getBytes()) ;
//    		            	 message.getMessageProperties().setCorrelationIdString(correlationId.toString()) ;
//    		            	 message.getMessageProperties().setConsumerTag("@34234234") ;
//    		            	 message.getMessageProperties().setConsumerQueue("123123") ;
//    		            	 message.getMessageProperties().setMessageId("234234234") ;
//    		            	 message.getMessageProperties().setConsumerQueue("111") ;
//    		            	 message.getMessageProperties().setReplyTo("111") ;
//    		            	 message.getMessageProperties().setConsumerTag("111") ;
//    		                message.getMessageProperties().setCorrelationId(correlationId) ;
//    		            	 mes = message ;
    		                return message;
    		            }} ;
    		        Message ms = rabbitTemplate.getMessageConverter().toMessage(x, new MessageProperties()) ;
//    		        ms.getMessageProperties().setReplyTo("123123123") ;
    		        ms.getMessageProperties().setConsumerQueue("111") ;
    		        ms.getMessageProperties().setConsumerTag("111") ;
    		        ms.getMessageProperties().setDelay(100000) ;
//    		        ms.getMessageProperties().setDeliveryMode(MessageDeliveryMode.)
    		        
    		        
    				list.add(correlationId) ;
    				String m = new String((byte[])rabbitTemplate.convertSendAndReceive(
    						"", "compute_queue", ms,ppm,correlationId)) ;
    				
//    				rabbitTemplate.send(routingKey, message) ;
//    				
//    				rabbitTemplate.receive(queueName)
    				
    				map.put(correlationId, m) ;
    			}
    		}).start() ;*/
//    	}
    	
    	/*try {
			Thread.sleep(1000) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	for(int i = 0 ; i < list.size() ; i++){
    		System.out.println(list.get(i) + "======" + (String)map.get(list.get(i)));
    	}*/
    }
    
    @RabbitListener(queues = SEND_QUEUE_NAME)
    public void processMsg(String content){
    	msg = content ;
    	System.out.println("content =====" + content);
    }
}
