<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%String resPath = (String)request.getAttribute("resPath");String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>管理员登录</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/admin/login.js"></script>
</head>
<body>
	<center>
	<div style="width:960px;text-align:left;">
	<form action="<%=basePath%>/pages/admin/login.action" method="POST">
	<table>
		<tr><td>用户名：</td><td><input id="username" name="username" type="text"/></td></tr>
		<tr><td>密&nbsp;&nbsp;码：</td><td><input id="password" name="password" type="password"/></td></tr>
	</table>
	<input id="login" type="submit" value="登录"/>
	</form>
	</div>
	</center>
</body>
</html>
