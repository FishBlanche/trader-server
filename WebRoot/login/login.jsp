<%@ page language="java" pageEncoding="utf-8"%>	
<%@ include file="/common/preRequisite.jsp" %> 	
<!DOCTYPE html> 
<head>
<title><fmt:message key="Title-login"></fmt:message></title>	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- ======= Main Stylesheet effects ======= -->
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/jquery.reject.css" rel="stylesheet" type="text/css" />					

<!-- ======= Javascript, cufon, slideshow jquery and purchsae now effects ======= -->
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/testNaviVersion.js"></script>											
<script type="text/javascript" src="../js/cufon-yui.js"></script>
<script type="text/javascript" src="../js/Helvetica_LT_Std_900.font.js"></script>
<script type="text/javascript" src="../js/jquery.faded.js"></script>
<script type="text/javascript" src="../js/custom.js"></script>
<script type="text/javascript" src="../js/jquery.ui.effect.js"></script>			
<script type="text/javascript" src="../js/jquery.ui.effect-shake.js"></script>
<script type="text/javascript" src="../js/jquery.reject.js"></script>		
<script type="text/javascript">
      $(function(){
          $("#slideshow").faded({
          speed: 500,
          autoplay: 5000
         });	
         
         function shake() {
			var options = {}; 	
		    $("#shake").effect("shake", options, 500, callback);		
		}
		
		function callback() { 
			$("input[name='pwd']").val("");													
		}	
							
		$( "#login-submit" ).click(function() {	
			var username = $("input[name='username']").val(); 
			var passwd = $("input[name='pwd']").val(); 
			
			if (username == "" || passwd == "")  {
				shake();
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
	    		
	    		$("#login-submit").click();					
	    	}	
	    });		
      });
 </script>
</head>
<body id="body">
<div id="totalarea">
  <div id="sitearea">
    <!-- ======= Header logo ======= -->
    <div id="header"> <a href="http://www.sensehuge.com/"><img src="../loginImages/header_logo_1.png" id="logo" alt="" /></a>			
	<!-- ======= Header Social networking icons ======= -->
	<ul id="social-icons">
            
    </ul>
 
	<!-- ======= Slideshow starts ======= -->      
      <div id="slideshow">
        <ul>
          <li> <img src="../loginImages/gallery-6.png" alt="" />
            <p></p>
          </li>
          <li> <img src="../loginImages/gallery-2.png" alt="" />
            <p></p>
          </li>
          <li> <img src="../loginImages/gallery-3.png" alt="" />
            <p></p>
          </li>
          <li> <img src="../loginImages/gallery-4.png" alt="" />
            <p></p>
          </li>
          <li> <img src="../loginImages/gallery-5.png" alt="" />
            <p></p>
          </li>
          <li> <img src="../loginImages/gallery-1.png" alt="" />
            <p></p>
          </li>
        </ul>
        <a href="#" class="prev">prev</a> <a href="#" class="next">next</a> </div>
	<!-- ======= Slideshow ends ======= -->      		
	<!-- ======= Featured box starts ======= -->
      <div id="featured-box">
        <div class="wrapper">
          <div class="wrapper-level2"> <strong>Features</strong> <b><img src="../loginImages/featured-img.png" alt="" /></b>			
            <ul>
              <li>监控各网络节点现状</li>
              <li>配置网络节点　</li>
              <li>配置节点的传感器信息　</li>
              <li> 批量导入节点配置信息 </li>
              <li> 安全访问</li>　						
            </ul>
         </div>														
        </div>
      </div>
      <ul id="featured">
        <li class="act"><a></a></li>
      </ul>
    </div>
	<!-- ======= Featured box ends ======= -->      	
    <!-- ======= Content area starts ======= -->      
    <div id="content-wrapper-level2">
      <div id="alignment">
    <!-- ======= Row 1 starts ======= -->   	  
        <div class="content-row1">
          <div class="wrapper">
            <div class="wrapper-level2">
              <div class="column-1">
              <img class="img-floatleft" alt="" src="../loginImages/about-01.jpg"  />  			
                <div class='title_strong'><strong>室内环境监控-系统配置</strong></div>
                <p><strong>室内环境监测系统</strong> 是Sensehuge基于无线传感网开发，实时对室内温度、湿度、光照、磁场信息，以及CO2，SO2等气体含量进行监控，
                		并可将实时监测数据与历史监测数据通过图表、动画等形式在pad、手机以及网页上向用户进行展示，方便用户及时了解室内环境状况。
                		<a href="http://www.sensehuge.com/">了解更多</a> </p> 					
              </div>
              <div class="column-2">
                <div class="box">
                  <div class="wrapper">
                    <div class="wrapper-level2">
                    <div id="shake">
                      <form id="newsletter-form">
	                        <div class="wrapper-level2"><p class="title_strong" style="font-size: x-large;">登录</p>
	                          <label> <b>用户名</b>
	                          <input name="username" type="text" class="text-field" placeholder="用户名" />
	                          </label>
	                          <label> <b>密码</b>
	                          <input name="pwd" type="password" class="text-field" placeholder="密码" />
	                          </label>   
	                          <span>请点击下载证书 <a href="<%=basePath %>/cert/ca-cert.cer">安全证书</a></span> 			
							   <div class="clear"></div>
	                          <input type="button" value="登录" id="login-submit" />	  			
	                          <div class="clear"></div> 
	                       </div> 
                      </form>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>  
          
    <!--  
    		
        <div class="content-row3">
          <div class="wrapper">
            <div class="wrapper-level2">
              <div class="column-1">
				　									
                				
                <div class="title_strong"><strong></strong> </div>
              </div>
              
            </div>
          </div>
        </div>
      	
      </div>
    </div>
    -->  
    <!-- ======= Content area ends ======= -->      	
    <!-- ======= Footer area starts ======= -->      
    <div id="footer">
      <div class="wrapper">
        <div class="wrapper-level2">
		  <!-- ======= Subscribe form starts ======= -->  		
   <!--        <form action="" id="subscribe-form">
          <input type="text" name="emailsub" id="emailsub" onblur="this.value=!this.value?'订阅到这个地方':this.value;" value="订阅" onclick="this.value='';"/>
          <input type="submit" value="订阅" id="subscribe-submit" />
          </form> -->
		  <!-- ======= Subscribe form ends ======= -->  		  		  
          <p>All rights Reserved - Copyright 2013 无锡赛思汇智科技有限公司 </p>  <!-- =======  Copyright info and footer logo in css ======= -->
		  <!-- ======= Footer menu starts ======= -->      
          <ul id="footer-menu">
          </ul>
          <!-- ======= Footer menu endss ======= -->  
          </div>
      </div>
    </div>
    <!-- ======= Footer area ends ======= -->      	
  </div>
</div>
    <!-- ======= Javascripts to start after html load ======= -->  
<script type="text/javascript"> Cufon.now(); </script>
</body> 
</html>