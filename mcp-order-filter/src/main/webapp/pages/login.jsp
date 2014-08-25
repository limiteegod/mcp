<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%String resPath = (String)request.getAttribute("resPath");%>
<%String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>**公司</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/login.js"></script>
<script type="text/javascript" src="<%=resPath%>/css/site.css"></script>
</head>
<body>
	<input id="basePath" type="hidden" value="<%=basePath%>"/>
	<center>
	<div style="width:800px;position:relative;text-align:left;">
		<div style="width:80px;float:left;position:relative;">message:</div><div style="width:720px;float:left;position:relative;"><textarea id="message" style="width:720px;"></textarea></div>
		<div style="width:800px;"><button id="submit">发布</button></div>
	</div>
	</center>
</body>
</html>
