<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/preRequisite.jsp" %> 									
<%@ include file="/common/common.jsp" %> 			
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    
    <title>
		<fmt:message key="Title-batProcessResult"></fmt:message>
	</title> 
    
    <link href="<%=basePath %>css/layout.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=basePath %>css/createLayout.css" rel="stylesheet" type="text/css" /> 					
    <link href="<%=basePath %>css/processLayout.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=basePath %>css/createNodeTypeLayout.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=basePath %>css/batProcessResult.css" rel="stylesheet" type="text/css" /> 	
    <link rel="shortcut icon" href="<%=basePath %>images/icon/favicon.ico" type="image/x-icon" />	
    									
</head>

<body> 
	<center>
	 <div id="wrapper">
     	<div id="layout">
        	<div id="header">
             
            	<jsp:include page="/common/header.jsp" /> 				
               	 
            </div>
            <div id="mainbar">
            </div>
            <div id="content">
            	<jsp:include page="/common/leftColumn.jsp" /> 	
                <div id="rightColumn">
                	<div class="cornRColTop"></div>	
                    <div class="pageContent"> 
                    	<form name="form" method="post" action="" encType="multipart/form-data" method="post">				
	                       	<ul class="pageMainContent">
	                       		<li><h1><fmt:message key="Welcome_to_BatProcess"></fmt:message></h1></li>
	                       		<li><div class="hr"></div></li>
	                       		<li>
	                       			<div class="row"> 
                       					<div class="label"><fmt:message key="Succeed_Import"></fmt:message>
                       						<%=request.getAttribute("rightNum") %>	Records
                       					</div>			 
                       				</div>	 
                       				<div class="hr"></div>	
                       				<div class="row"> 
                       					<div class="label"><fmt:message key="Failure_Import"></fmt:message>
                       						<%=request.getAttribute("errorNum") %> Records
                       					</div>	
                       					<div style="color: white;">		
                       					<div class="input bias">										 
                       					<%	
                       						List errorList = (ArrayList)request.getAttribute("errorList");
                       						int num = errorList.size();						
                       						for (int i = 0; i < num; ++ i) { 
                       							
                       					%>
                       							<%=errorList.get(i) %>							
                       							 <br />													
                       					<% 	
                       						} 
                       					 %>
                       					 </div>				
										</div> 
                       				</div>	 
	                       		</li>  	
	                       		<li><div class="hr"></div></li>				
	                       		<li><a name="submit" href="<%=basePath %>batProcess/batFileProcess.jsp" 		
	                       								class="button grey"><fmt:message key="Return_it"></fmt:message></a></li>																		
	                       	</ul>							
	                    </form>			 					
                        <br /><br /><br /><br /><br /><br /> 
                        <br /><br /><br /><br /><br /><br /> 
                        <br /><br /><br /><br /><br /><br /> 	
                    </div>
                    <div class="cornRColBot"></div>	
                </div>
                <div class="clear"></div>
            </div> 	
        </div>	
        <div class="clear"></div>  
     </div>
     <div id="bottom">
        <div id="footer">
            <div class="imprint">All rights Reserved - Copyright GreenOrbs 2013</div>	
        </div>
     </div>
     </center>
</body>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.8.3.js"></script>			
<script type="text/javascript" src="<%=basePath %>js/jquery.filestyle.js"></script>	

<script type="text/javascript">

	$(document).ready(function(){ 
		$('ul.menu').css('display', 'block');  
		$('div.menuHeaderLink').toggle(
			function() {  
				$('ul.menu').css('display', 'block');   
			}, function() { 
				$('ul.menu').css('display', 'none');   	
			} 
		); 	
		
		$('ul.menu > li a img').hover(function() {  
			var originPicNameParts = $(this).attr('src').split('.');
			var picName = originPicNameParts[0] + "." + $(this).parents('a').attr('name') + "." + originPicNameParts[1]; 			
			
			$(this).attr('src', picName); 								
		}, function() { 
			var originPicNameParts = $(this).attr('src').split('.');
			var picName = originPicNameParts[0] + "." + originPicNameParts[2]; 			
			
			$(this).attr('src', picName); 		
		});	
	});	
</script>

</html>