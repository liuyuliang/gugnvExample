package com.gugnv.example.activemq.topics;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 持久订阅模式
 * 
 * @author liuyuliang
 * @date 2015年4月1日 下午1:47:59
 */
public class TopicPublisher2 {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		// 创建topic连接
		TopicConnection connection = factory.createTopicConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("myTopic.messages");
		MessageProducer producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);

		while (true) {
			TextMessage message = session.createTextMessage();
			message.setText("我是中国人" + System.currentTimeMillis());
			producer.send(message);
			System.out.println("Sent message: " + message.getText());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// session.close();
		// connection.stop();
		// connection.close();
	}
}