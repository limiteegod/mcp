<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String resPath = (String)request.getAttribute("resPath");String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>管理员登录</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/view_by_id.js"></script>
<script type="text/javascript" src="<%=resPath%>/css/site.css"></script>
</head>
<body>
	<input id="article_id" value="${article.id}" type="hidden"/>
	<input id="basePath" type="hidden" value="<%=basePath%>"/>
	<center>
	<div style="text-align:left;width:960px;">
		<div><a href="<%=basePath%>/pages/list.htm">返回列表</a></div>
		<div style="font-weight:bold;font-size:20px;">${article.title}</div>
		<div>${article.content}</div>
	</div>
	</center>
</body>
</html>
