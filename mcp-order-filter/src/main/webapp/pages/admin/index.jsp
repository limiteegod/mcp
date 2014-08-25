<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="utf-8"%>
<%String resPath = (String)request.getAttribute("resPath");String basePath = (String)request.getAttribute("basePath");%>
<html>
<head>
<title>管理员登录</title>
<script type="text/javascript" src="<%=resPath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=resPath%>/js/admin/index.js"></script>
</head>
<body style="margin:0px;">
	<iframe id="head" name="head" src="<%=basePath%>/pages/admin/head.htm" frameBorder="0" style="width:100%;height:40px;border-bottom:1px solid black;"></iframe>
	<iframe id="left" name="left" src="<%=basePath%>/pages/admin/left.htm" frameBorder="0" style="width:130px;border-right:1px solid black;"></iframe>
	<iframe id="cindex" name="cindex" src="<%=basePath%>/pages/admin/cindex.htm" frameBorder="0" style="width:80%;border:none;"></iframe>
</body>
</html>
