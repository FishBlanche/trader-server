$(function() {	
//	alert($.browser.version); 
//	alert($.browser.versionX); 		
//	alert($.os.name); 
				
	if ($.browser.name == 'msie' && $.browser.versionX < 9) { 		  											
				
		$.reject({  
			reject: {  
				all: false,									
				msie: true, // Microsoft Internet Explorer  
			},   
			overlayBgColor: '#efe',						
			header: '警告', 		
			overlayOpacity: 0.8, 
			paragraph1: '您现在正在使用IE内核浏览器，版本较低，影响显示效果，建议您升级或者使用以下推荐浏览器', 
			paragraph2: '点击即可进入下载页面',
			closeLink: '关闭此窗口或者使用ESC键',
			closeMessage: '', 
			imagePath: '../images/browsers/',										
		}); // Customized Browsers  
	}			
}); 							