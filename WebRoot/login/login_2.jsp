<%@ page language="java" pageEncoding="utf-8"%>		
<%@ include file="/common/preRequisite.jsp" %> 		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="Title-login"></fmt:message></title>								
<link rel="stylesheet" href="../css/login.css" />		
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script> 		
<script type="text/javascript" src="../js/jquery.ui.effect.js"></script>			
<script type="text/javascript" src="../js/jquery.ui.effect-shake.js"></script>	
			
<script type="text/javascript">
	$(function() { 
	 
		function shake() {
			var options = {}; 	
		    $("#shake").effect("shake", options, 500, callback);		
		}
		
		function callback() { 
			$("input[name='pwd']").val("");													
		}	
							
		$( "#button" ).click(function() {	
			var username = $("input[name='username']").val(); 
			var passwd = $("input[name='pwd']").val(); 
			
			if (username == "" || passwd == "")  {
				
				return false; 			
			}										
		    $.get("<%=basePath %>dataSource?time="+new Date().getTime()+"&dealtype=login&username=" + username + "&pwd=" + passwd, 					
		      		function(data) { 
						if (data == "false") { 
							
							shake();					
						}									
						else { 
							
							top.location = <%=basePath %> + data;																								
						}	
		      		}); 									
		      return true;										
	    });		
	    
	    $("body").keydown(function() { 
	    	
	    	if (event.keyCode == "13") { 
	    		
	    		$("#button").click();					
	    	}	
	    });		
	}); 
	
</script>	
</head>
<body>
	<nav><a href="#" class="focus">登录</a> | <a href="register.jsp">注册</a></nav>						

	<div id="shake">
		<form>
			<h2>登录</h2>
	
			<input name="username" type="text" class="text-field" placeholder="用户名" />
			<input name="pwd" type="password" class="text-field" placeholder="密码" />
			
			<input type="button" id="button" value="登录" class="button" />			
			<div>
				Please click the link: <a href="<%=basePath %>/cert/ca-cert.cer">Authentication</a>				
			</div>										
		</form>
	</div>		
</body>
</html>