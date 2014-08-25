<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String resPath = (String)request.getAttribute("resPath");String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>管理员登录</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/list.js"></script>
<script type="text/javascript" src="<%=resPath%>/css/site.css"></script>
</head>
<body>
	<input id="basePath" type="hidden" value="<%=basePath%>"/>
	<center>
	<div style="text-align:left;width:960px;">
		<div><button id="add">新增</button></div>
		<table>
		<tr><td style="border:1px solid black;">id</td><td style="border:1px solid black;">标题</td><td style="border:1px solid black;">操作</td></tr>
		<c:forEach var="article" items="${articleList}">
		    <tr dataid="${article.id}"><td>${article.id}</td><td><a href="<%=basePath%>/pages/view_by_id.htm?articleId=${article.id}" style="cursor:pointer;">${article.title}</a></td><td><a class="deleteButton" href="javascript:" dataid="${article.id}">删除</a></td></tr>
			<tr dataid="${article.id}"><td style="border-bottom:1px solid black;" colspan="3"></td></tr>
		</c:forEach>
		</table>
		<div>共${count}条记录</div>
	</div>
	</center>
</body>
</html>
