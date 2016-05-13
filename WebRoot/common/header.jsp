<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/preRequisite.jsp" %> 					
<%@ include file="/common/common.jsp" %> 		
<div id="header">
	<div id="logo">
    	<h1><img src="<%=basePath %>images/heager_logo.png"/></h1>						
    </div>			
    <div id="login">
    	<a href="<%=basePath %>index.jsp" style="float: left; margin: 0 0 0 50px;">主页</a>
    	<a href="<%=basePath %>dataSource?dealtype=logout">退出</a>																								
    </div>			
</div>	