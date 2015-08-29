<%@page import="javax.jms.Message"%>
<%@page import="javax.jms.TopicSubscriber"%>
<%@page import="javax.jms.TextMessage"%>
<%@page import="javax.jms.JMSException"%>
<%@page import="javax.jms.MessageListener"%>
<%@page import="javax.jms.Topic"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.Connection"%>
<%@page import="org.apache.activemq.ActiveMQConnectionFactory"%>
<%@ page
	language="java"
	import="java.util.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'demo.jsp' starting page</title>
<meta
	http-equiv="pragma"
	content="no-cache">
<meta
	http-equiv="cache-control"
	content="no-cache">
<meta
	http-equiv="expires"
	content="0">
<meta
	http-equiv="keywords"
	content="keyword1,keyword2,keyword3">
<meta
	http-equiv="description"
	content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>
<div style="height:400px;width:600px;border:block;overflow:auto" id="msg">
</div>
	<%
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.191.1:61616");
		Connection connection = factory.createConnection();
		connection.setClientID("computer1");
		connection.start();

		Session sessions = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = sessions.createTopic("myTopic.messages");
		TopicSubscriber subscriber = sessions.createDurableSubscriber(topic, "luck");
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
	%>
</body>
</html>
