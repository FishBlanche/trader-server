<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/preRequisite.jsp" %> 					
<%@ include file="/common/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    
    <title>
    	<fmt:message key="Title-batFileProcess"></fmt:message>
    </title> 
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
   									<div class="upload_sample"> 批处理文件下载</div>								
   									 <p>
   									 	<a href="<%=basePath %>sample/NodeType.txt" style="float: left; color: red;">导入样本.txt</a>
   									 </p>									
	                       		</li>
	                       		<li><div class="hr"></div></li>
	                       		<li>
   									 <input class="black upload" type="file" name="fileName" accept="text/plain" />									
	                       		</li>  	
	                       		<li><div class="hr"></div></li>
	                       		<li><a name="submit" href="javascript:form.action='<%=basePath %>dataSource?dealtype=analyse';form.submit();" 
	                       								class="button grey"><fmt:message key="Analyse_it"></fmt:message></a>
	                       			<a href="<%=basePath %>index.jsp" class="button grey"><fmt:message key="Return_it"></fmt:message></a>						
	                       		</li>																		
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
<link href="<%=basePath %>css/layout.css" rel="stylesheet" type="text/css" /> 
<link href="<%=basePath %>css/createLayout.css" rel="stylesheet" type="text/css" /> 					
<link href="<%=basePath %>css/processLayout.css" rel="stylesheet" type="text/css" />  
<link rel="shortcut icon" href="<%=basePath %>images/icon/favicon.ico" type="image/x-icon" />	
<script type="text/javascript" src="<%=basePath %>js/jquery-1.8.3.js"></script>			
<script type="text/javascript" src="<%=basePath %>js/jquery.filestyle.js"></script>		
<script type="text/javascript">
	$(function() {
      $("input.upload").filestyle({ 
          image: "<%=basePath %>images/select-file_1.png",						
          imageheight : 30,
          imagewidth : 40,		
          width : 220		
      }); 
	});
      		
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