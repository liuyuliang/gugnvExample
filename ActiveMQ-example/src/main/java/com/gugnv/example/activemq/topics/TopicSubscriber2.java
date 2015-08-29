package com.gugnv.example.activemq.topics;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicSubscriber2 {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.191.1:61616");
		Connection connection = factory.createConnection();
		connection.setClientID("computer2");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//session.unsubscribe("tom");// 取消订阅
		// Topic topic = session.createTopic("myTopic.messages");
		Topic topic = session.createTopic("myTopic.messages");
		javax.jms.TopicSubscriber subscriber = session.createDurableSubscriber(topic, "liuliu");
		subscriber.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				try {
					message.acknowledge();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
				TextMessage tm = (TextMessage) message;
				try {
					System.out.println("Received message: " + tm.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		//
	}
}