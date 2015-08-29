package com.gugnv.example.activemq;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageReceiver implements MessageListener{
	private static String URL = "tcp://127.0.0.1:61616";
	private static String QUEUE_NAME = "test2_queue";
	private static String TOPIC_NAME="test.topic";
	
	public void onMessage(Message msg) {
		if(null != msg && msg instanceof TextMessage){
			try {
				System.out.println("Message : " + ((TextMessage)msg).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	public void receiceMsg() {
		ActiveMQConnectionFactory factory = 
			new ActiveMQConnectionFactory(URL);
		try {
			Connection conn = factory.createConnection();
			conn.start();
			Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = sess.createQueue(QUEUE_NAME);
			//Destination dest = sess.createTopic(TOPIC_NAME);
			MessageConsumer consumer = sess.createConsumer(dest);
			consumer.setMessageListener(this);
			/*Message msg = consumer.receive();
			if(null != msg && msg instanceof TextMessage)
				System.out.println("Message : " + ((TextMessage)msg).getText());*/
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		MessageReceiver receiver = new MessageReceiver();
		receiver.receiceMsg();
	}
}
