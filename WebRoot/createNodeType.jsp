<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
<%@ include file="/common/preRequisite.jsp" %> 					
<%@ include file="/common/common.jsp" %> 
<jsp:useBean id="nodeTypeBean" class="po.NodeTypeBean" scope="request">
</jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    
    <title><fmt:message key="Title-createNodeType"></fmt:message></title> 
    
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
                    	<form name="form" method="post" action="">
	                       	<ul class="pageMainContent"> 
	                       		<li><h1><fmt:message key="createNodeType_Welcome_to_Create_NodeType"></fmt:message></h1></li> 
	                       		<li><div class="hr"></div></li> 
	                       		<form method="post" name="form">		 
	                       			<div class="table form-fields"> 
	                       				<input type="hidden" name="createSession" value="${param.createSession}" />	
	                       				<input type="hidden" name="a_moteid_ID" value="${param.moteid_ID}" />	
	                       				<input type="hidden" name="a_location_X" value="${param.location_X}" />			
	                       				<input type="hidden" name="a_location_Y" value="${param.location_Y}" />	
	                       				<input type="hidden" name="a_latitude" value="${param.latitude}" />			
	                       				<input type="hidden" name="a_latitude" value="${param.longitude}" />	
	                       				<input type="hidden" name="a_nodeType" value="${param.nodeType}" />		
                       					<input type="hidden" name="a_remarks" value="${param.remarks}" />					
	                       				<div class="row"> 
	                       					<div class="label"><fmt:message key="createNodeType_Node_Type"></fmt:message></div>			 
	                       					<div class="input">			 
	                       						<input name="nodeType" type="text" value="${nodeTypeBean.nodeType}" class="black" />  							 
	                       					</div>	 
	                       				</div>	 
	                       				 <div class="hr"></div>
	                       				<div class="row">			 
	                       					<div class="label"><fmt:message key="createNodeType_Related_SensorName"></fmt:message></div>	 
	                       					<input name="selectedValues" type="hidden" />		
	                       					<br /> 
	                       					<div class="sensorNameDes">			 
	                   							 <fieldset class="checkboxes"> 	
	                   							 	<c:forEach items="${nodeDBBean.sensorTypeList}" var="current">				
		                       							<label class="label_check"> 
		                       								<h3><c:out value="${current.sensorName}"></c:out></h3>	
		                       								<img name="${current.sensorName}" src="images/unselectedCheckbox.gif" />	 
		                       							</label> 
		                       							<br/> 
		                       						</c:forEach>	
		                       						<label class="label_check label_link"> 		
	                       								<h3><a href="javascript:form.action='createSensorType.jsp?createSession=1';form.submit();"><fmt:message key="more"></fmt:message>...</a></h3>				 					 
	                       							</label> 
	                       							<br/>								
	                       						 </fieldset>							
	                       					</div>			 
	                       				</div>	 
	                       				 <div class="hr"></div>
	                       				<div class="row"> 
	                       					<div class="label"><fmt:message key="createNodeType_Remarks"></fmt:message></div> 
	                       					<div class="input"> 
												<textarea name="remarks" rows="30" cols="38" class="black">${nodeTypeBean.remark}</textarea>							 
	                       					</div>		 
	                       				</div> 
	                       			</div>	 
	                       		</form>	 
	                       		<li><div class="hr"></div></li> 
	                       		<li><a href="javascript:form.action='dataSource?dealtype=createFinalNodeType';form.submit();" class="button grey" onclick="setCheckboxValues()"><fmt:message key="Create_it"></fmt:message></a>
	                       			<%
	                       				String createSession = (String)request.getParameter("createSession"); 		
	                       				String path = basePath + "index.jsp"; 
	                       				if ("2".equals(createSession)) { 		
	                       										
	                       					path = "javascript:form.action='dataSource?dealtype=createFinalNodeType&plugonCommand=no&createSession=2';form.submit();"; 								
	                       				}  	
	                       			 %>
	                       			<a href="<%=path %>" class="button grey"><fmt:message key="Return_it"></fmt:message></a>	
	                       		</li>														 
	                       	</ul>
	                    </form>			 					
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