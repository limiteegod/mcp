<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String resPath = (String)request.getAttribute("resPath");String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>管理员登录</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/admin/viewAllUser.js"></script>
<script type="text/javascript" src="<%=resPath%>/css/admin/viewAllUser.css"></script>
</head>
<body>
	<div>
	<table>
	<tr><td style="border:1px solid black;">id</td><td style="border:1px solid black;">名称</td><td style="border:1px solid black;">年龄</td><td style="border:1px solid black;">操作</td></tr>
	<c:forEach var="user" items="${userList}">
	    <tr><td>${user.id}</td><td>${user.name}</td><td>${user.age}</td><td><a class="deleteButton" href="javascript:" userid="${user.id}">删除</a></td></tr>
	</c:forEach>
	</table>
	</div>
</body>
</html>
