<%			
		String path1 = request.getContextPath();
		String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";	
		
		String loginOrNot = (String)session.getAttribute("LoginOrNot"); 
		if (!"Y".equals(loginOrNot)) { 													
			response.sendRedirect(basePath1 + "login/login.jsp"); 					
		}  							
%>				