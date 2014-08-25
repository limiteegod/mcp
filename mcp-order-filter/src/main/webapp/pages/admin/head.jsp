<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%String resPath = (String)request.getAttribute("resPath");String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>管理员登录</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/admin/head.js"></script>
</head>
<body>
	<center>
	<div style="width:960px;text-align:left;">
	欢迎，<%=session.getAttribute("username") %>
	</div>
	</center>
</body>
</html>
