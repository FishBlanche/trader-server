<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/preRequisite.jsp" %> 	 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="Title-register"></fmt:message></title>
									
<link rel="stylesheet" href="../css/login.css" />	 		
</head>
<body>
	<nav><a href="login.jsp">登录</a> | <a href="#" class="focus">注册</a></nav>
						
	<form class="register">

		<h2>注册</h2>
					
		<input type="text" class="text-field" placeholder="First Name" />
	    <input type="text" class="text-field" placeholder="Last Name" />
	    <input type="email" class="text-field" placeholder="E-mail" />
	    <input type="password" class="text-field" placeholder="Password" />
	    <input type="password" class="text-field" placeholder="Repeat Password" />
	    
	    <input type="button" value="Register Account" class="button" />
	
	</form>	
</body>
</html>