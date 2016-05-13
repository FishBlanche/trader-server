<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>			
<%@ include file="/common/preRequisite.jsp" %> 					
<%@ include file="/common/common.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    
    <title><fmt:message key="Title-modifyNodeType"></fmt:message></title>  
    
    <link href="css/layout.css" rel="stylesheet" type="text/css" /> 
    <link href="css/createNodeTypeLayout.css" rel="stylesheet" type="text/css" /> 
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
                       	<ul class="pageMainContent"> 
                       		<li><h1><fmt:message key="modifyNodeType_Welcome_to_Modify_NodeType"></fmt:message></h1></li> 
                       		<li><div class="hr"></div></li> 
                       		<form method="post" name="form">		 
                       			<div class="table form-fields"> 
                       				<div class="row"> 
                       					<div class="label"><fmt:message key="createNodeType_Node_Type"></fmt:message></div>	  
										<div class="styled-select">
											<select name="nodeType"> 
												<option>--Choose--</option>
												<c:forEach items="${nodeDBBean.nodeTypeList}" var="current">
												<option>
													<c:out value="${current.nodeType}"></c:out>		
												</option>		
												</c:forEach>
											</select>	
										</div>
                       				</div>	 
                       			</div>	 
                       		</form>	 
                       		<li><div class="hr"></div></li> 
                       		<li><a href="javascript:form.action='dataSource?dealtype=modifyNodeTypeDir';form.submit();" class="button grey"><fmt:message key="Proceed"></fmt:message></a></li>														 
                       	</ul>	 																			
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
		}) ; 	
		
		function setUpLabel() { 
			$('img.clickImg').each(function() { 
				$('img.clickImg').attr('src', 'images/unselectedRadio.png'); 	
			}); 	
		}			
		
		$('ul.menu > li a img').hover(function() {  
			var originPicNameParts = $(this).attr('src').split('.');
			var picName = originPicNameParts[0] + "." + $(this).parents('a').attr('name') + "." + originPicNameParts[1]; 			
			
			$(this).attr('src', picName); 					
		}, function() { 
			var originPicNameParts = $(this).attr('src').split('.');
			var picName = originPicNameParts[0] + "." + originPicNameParts[2]; 			
			
			$(this).attr('src', picName); 		
		});	
		
		$('fieldset.checkboxes label img').toggle(function() { 
			$(this).attr('src', 'images/selectedCheckbox.gif'); 	
		}, function() { 
			$(this).attr('src', 'images/unselectedCheckbox.gif'); 				
		});   
	});	
	
	function setCheckboxValues() { 
		var text = $('input[name="selectedValues"]').val(); 
		
		if (text == null) { 
		
			text = ""; 
		} 
		else if (text != "") { 
		
			text += ","; 
		}
		$('fieldset.checkboxes label img').each(function(){
			
			if ($(this).attr("src") == 'images/selectedCheckbox.gif') { 	
				
				text += $(this).attr("name") + ","; 						
			}	
		});	
		$('input[name="selectedValues"]').val(text); 
	}			
</script>

</html>