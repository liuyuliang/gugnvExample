package com.gugnv.example.activemq.topics;
 
import org.apache.activemq.ActiveMQConnectionFactory;
 

import javax.jms.*;
 
public class TopicSubscriber {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.191.1:61616");
        Connection connection = factory.createConnection();
        connection.start();
         
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
       // Topic topic = session.createTopic("myTopic.messages");
        Topic topic = session.createTopic("test/topic");
        
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
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
//      session.close();
//      connection.stop();
//      connection.close();
    }
}