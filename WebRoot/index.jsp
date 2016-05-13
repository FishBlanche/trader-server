<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
<%@ include file="/common/preRequisite.jsp" %> 					
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    
    <title><fmt:message key="Title-index"></fmt:message></title> 
    
    <link href="css/layout.css" rel="stylesheet" type="text/css" /> 
    <link href="css/indexRectify.css" rel="stylesheet" type="text/css" /> 	
    <link rel="shortcut icon" href="images/icon/favicon.ico" type="image/x-icon" />			
	<link href="media/dataTables/demo_page.css" rel="stylesheet" type="text/css" />
    <link href="media/dataTables/demo_table.css" rel="stylesheet" type="text/css" />				
    <link href="media/dataTables/demo_table_jui.css" rel="stylesheet" type="text/css" />
    <link href="media/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" media="all" />
    <link href="media/themes/smoothness/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" media="all" />
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>		
    <script src="scripts/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="scripts/portamento.js"></script> 						
    <script type="text/javascript">
	    $(document).ready(function () {	
	       $("#nodeInfo").dataTable({
	            "sPaginationType": "full_numbers",
	            "bJQueryUI": true,
				"bProcessing": true,
				 "oLanguage": {
	               "sProcessing": "正在加载中......",
	               "sLengthMenu": "每页显示 _MENU_ 条记录",
	               "sZeroRecords": "对不起，查询不到相关数据！",
	               "sEmptyTable": "表中无数据存在！",
	               "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	               "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
	               "sSearch": "搜索",
	               "oPaginate": {
	                   "sFirst": "首页",
	                   "sPrevious": "上一页",
	                   "sNext": "下一页",
	                   "sLast": "末页"
	               }
	            }						
	        });	
	       nodeInfoTable = $("#nodeInfo").dataTable();						
	       nodeInfoTable.fnSetColumnVis( 6, true );	
	       $("#sensorInfo").dataTable({		
	            "sPaginationType": "full_numbers",
	            "bJQueryUI": true,
	            "bRetrieve": true,
				 "oLanguage": {
	               "sProcessing": "正在加载中......",
	               "sLengthMenu": "每页显示 _MENU_ 条记录",
	               "sZeroRecords": "对不起，查询不到相关数据！",
	               "sEmptyTable": "表中无数据存在！",
	               "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	               "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
	               "sSearch": "搜索",
	               "oPaginate": {
	                   "sFirst": "首页",
	                   "sPrevious": "上一页",
	                   "sNext": "下一页",
	                   "sLast": "末页"
	               }
	            }													
	        });	
	       sensorInfoTable = $("#sensorInfo").dataTable();  
	       sensorInfoTable.fnSetColumnVis( 4, false );
	        $("#nodeInfo tbody tr").live('click', function( e ) {
		        if ( $(this).hasClass('row_selected') ) {		
		        
		            $(this).removeClass('row_selected');								
		        }								
		        else {					
		        								
		            nodeInfoTable.$('tr.row_selected').removeClass('row_selected');
		            $(this).addClass('row_selected');						
		            var sData = nodeInfoTable.fnGetData( this );				
	        		sensorInfoTable.fnFilter(sData[6], 4);															
		        }			
		    });	
		    
		    $('#sidebar').portamento({disableWorkaround: true}); 
	    }); 
    </script>	
</head>
<%	
							if ("Y".equals(loginOrNot) && nodeDBBean != null && nodeDBBean.isEmpty()) { 
						%>
								<jsp:forward page="dataSource?dealtype=ying"></jsp:forward>				
						<% 
							}  
							else 
						 %>
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
	                       		<li><h1>
	                       			<fmt:message key="index_Welcome_to_CONFIGURE_PAGE"></fmt:message>	
	                       		</h1></li>
	                       		<li>
	                       			<div id="dt_example">
	                       				<div id="container">
	                       					<div id="demo_jui">
	                       						<table id="nodeInfo" class="display">
										            <thead>
										                <tr>
										                    <th><fmt:message key="moteid_ID"></fmt:message></th>
										                    <th><fmt:message key="Location_X"></fmt:message></th>
										                    <th><fmt:message key="Location_Y"></fmt:message></th>																					
										                    <th><fmt:message key="Latitude"></fmt:message></th>
										                    <th><fmt:message key="Longitude"></fmt:message></th>
										                    <th><fmt:message key="Remarks"></fmt:message></th>	
										                    <th>NodeType</th>
										                    <th><fmt:message key="Edit"></fmt:message></th>																	
										                </tr>
										            </thead>
										            <tbody>
										            	<%
										            		List<NodeInformationBean> list = nodeDBBean.getNodeInfoList(); 	
										            		for (int i = 0; i < list.size(); i ++) { 
										            			
										            			String moteid = list.get(i).getMoteid_ID(); 
										            	 %>
										            	<tr>
											          		<td><%=moteid %></td>
										                    <td><%=list.get(i).getLocation_X() %></td>
										                    <td><%=list.get(i).getLocation_Y() %></td>	
										                    <td><%=list.get(i).getLatitude() %></td>						
										                    <td><%=list.get(i).getLongitude() %></td>									
										                    <td><%=list.get(i).getRemarks() %></td>
										                    <td><%=list.get(i).getNodeType() %></td>
										                    <td><a href="<%=basePath %>dataSource?dealtype=modifyNodeInfoDir&moteid_ID=<%=moteid %>">
										                    			<fmt:message key="Edit"></fmt:message></a>
										                    </td>														
									                    </tr> 
									                    <%
									                    	}
									                     %>
										            </tbody>
										        </table>
										        <br />
										        <table id="sensorInfo" class="display">
										            <thead>
										                <tr>
										                    <th><fmt:message key="createSensorType_SensorType"></fmt:message></th>
										                    <th><fmt:message key="createSensorType_Unit"></fmt:message></th>
										                    <th><fmt:message key="createSensorType_Low_Range"></fmt:message></th>
										                    <th><fmt:message key="createSensorType_High_Range"></fmt:message></th>																					
										                    <th>Belonged NodeType</th>
										                    <th><fmt:message key="Edit"></fmt:message></th>															
										                </tr>
										            </thead>
										            <tbody>
										            	<%
										            		List<SensorTypeBean> sensorList = nodeDBBean.getSensorTypeList(); 	
										            		for (int i = 0; i < sensorList.size(); i ++) { 
										            									
										            			String sensorName = sensorList.get(i).getSensorName(); 				
										            	 %>
										            	<tr>
											          		<td><%=sensorName %></td>					
										                    <td><%=sensorList.get(i).getUnit() %></td>					
										                    <td><%=sensorList.get(i).getRangeLow() %></td>
										                    <td><%=sensorList.get(i).getRangeHigh() %></td>				
										                    <td><%=nodeDBBean.relatedSensor_Node(sensorName) %></td>		
										                    <td><a href="<%=basePath %>dataSource?dealtype=modifySensorTypeDir&sensorName=<%=sensorName %>">
										                    			<fmt:message key="Edit"></fmt:message></a>
										                    </td>												
									                    </tr> 
									                    <%
									                    	}
									                     %>
										            </tbody>
										        </table>
										    </div>
	                       				</div>
	                       			</div>	
	                       		</li>	
							</ul>
	                    </form>					
              			 <br /> 	
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