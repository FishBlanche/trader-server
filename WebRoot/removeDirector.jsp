<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/preRequisite.jsp" %> 					
<%@ include file="/common/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    
    <title><fmt:message key="Title-removeDirector"></fmt:message></title> 
    
    <link href="css/layout.css" rel="stylesheet" type="text/css" /> 
    <link href="css/createLayout.css" rel="stylesheet" type="text/css" /> 
    <link rel="shortcut icon" href="images/icon/favicon.ico" type="image/x-icon" />			
</head>

<body> 
	<center>
	 <div id="wrapper">
     	<div id="layout">
        	<jsp:include page="/common/header.jsp" /> 								
            <div id="mainbar">
            </div>
            <div id="content">
            	<jsp:include page="/common/leftColumn.jsp" />
                <div id="rightColumn">
                	<div class="cornRColTop"></div>	
                    <div class="pageContent"> 
                    	<form name="form" method="post" action="">
	                       	<ul class="pageMainContent">
	                       		<li><h1><fmt:message key="removeDirector_Welcome_to_Remove"></fmt:message></h1></li>
	                       		<li><div class="hr"></div></li>
	                       		<li><img class="clickImg" name="NodeType" src="images/unselectedRadio.png" /><h2><fmt:message key="NodeType"></fmt:message></h2></li>			
	                       		<li><img class="clickImg" name="SensorType" src="images/unselectedRadio.png" /><h2><fmt:message key="SensorType"></fmt:message></h2></li>	
	                       		<li><img class="clickImg" name="NodeInfo" src="images/unselectedRadio.png" /><h2><fmt:message key="Node_Info"></fmt:message></h2></li>	
	                       		<li><div class="hr"></div></li>
	                       		<li><input name="selectedValue" type="hidden" /></li>								
	                       		<li><a name="submit" href="javascript:form.action='dataSource?dealtype=remove';form.submit();" class="button grey"><fmt:message key="Proceed"></fmt:message></a></li>																		
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
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>	
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
		
		$('img.clickImg').click(function(){ 
			setUpLabel(); 	
			$(this).attr('src', 'images/selectedRadio.png'); 
			
			$('input[name="selectedValue"]').val($(this).attr("name")); 
		}) ; 	
		
		function setUpLabel() { 
			$('img.clickImg').each(function() { 
				$('img.clickImg').attr('src', 'images/unselectedRadio.png'); 	
			}); 	
		}			
		
		$('a[name="submit"]').click(function() { 
			
			if ($('input[type="hidden"]').val() == "") { 
				alert("Your should select one option");							 
				return false;				
			}	
		});
						
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