<%@ page
	language="java"
	import="java.util.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'index.jsp' starting page</title>
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
<script
	type="text/javascript"
	src="js/jquery-1.10.2.js"></script>
<script
	type="text/javascript"
	src="js/amq_jquery_adapter.js"></script>
<script
	type="text/javascript"
	src="js/amq.js"></script>
</head>
<body>
聊天室:
<hr>
<div style="height:400px;width:600px;border:block;overflow:auto" id="msg">
</div>
<br>
<script type="text/javascript">
var amq = org.activemq.Amq;
amq.init({ 
  uri: 'amq', 
  logging: true,
  timeout: 20,
  clientId:"computer2"
});
var myHandler =
{
  rcvMessage: function(message)
  {
      //alert("received data:"+message.text);
      //alert("message.textContent :"+message.textContent)
      //alert("message.childNodes.item(0).nodeValue:"+message.childNodes.item(0).nodeValue)   兼容IE和CHROME
      //alert("message.childNodes[0].nodeValue:"+message.childNodes[0].nodeValue);   兼容IE和CHROME
      if(message.childNodes.length > 0)       //web页面发送的消息
          document.getElementById("msg").innerHTML += message.childNodes[0].nodeValue + "<br>";
      else      //java代码发送的消息
          document.getElementById("msg").innerHTML += message.nodeValue + "<br>";
  }
};
//{selector:"user='liyang'"}
amq.addListener("liuliu","topic://myTopic.messages",myHandler.rcvMessage,null);
//amq.addListener("smeguangdong","topic://FirstTopic",myHandler.rcvMessage);
function go()
{
    var nickname = document.getElementById("nickname").value;
    var content = document.getElementById("keymsg").value;
    var ms = nickname + " : " +content;
    //alert("msg is "+ms);
    amq.sendMessage("topic://myTopic.messages","<message>"+ms+"</message>","amq-msg-type=>'text'");
    }
</script>
昵称：
<input type="text" id="nickname">
内容：
<input type="text" id="keymsg">
<button onclick="go()">submit</button>
</body>
</html>
