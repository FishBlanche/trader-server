<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ page isErrorPage="true" %>
<%@ include file="/common/preRequisite.jsp" %> 	
<html>
  <head>
    <title>Sorry</title>					
    <link rel="shortcut icon" href="<%=basePath %>images/icon/favicon.ico" type="image/x-icon" />	
  </head>
  <body bgcolor="black">
    <h3 style="font: italic;color: white;">we're sorry but the request could not be processed. 
    Detailed information about the error has been logged so we will					
    analyze it and correct whatever is causing it as soon as possible.
    Please try again, and 
    <a href="mailto:874827643@qq.com">let us know</a> if the
    problem persists.								
    </h3>
	<a href="<%=basePath %>index.jsp">返回</a> 	  				  
</body>
</html>