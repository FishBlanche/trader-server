/* Cufon replacement configuration begins */
Cufon.replace('h2, h3, #menu li, #featured-box strong, .title, .title-1, #newsletter-form strong, #slideshow p', { fontFamily: 'Helvetica LT Std', hover: 'true' });
Cufon.replace('#slideshow p', { fontFamily: 'Helvetica LT Std', textShadow: '#1c1c1c 1px 1px', hover: 'true' });
/* Cufon replacement configuration endss */

/* Purchase Now button fadein effect begins */
$(document).ready(function () {

	//Append a div with hover class to all the LI
	$('#purchase-now li').append('<div class="hover"><\/div>');


	$('#purchase-now li').hover(
		
		//Mouseover, fadeIn the hidden hover class	
		function() {
			
			$(this).children('div').fadeIn('1000');	
		
		}, 
	
		//Mouseout, fadeOut the hover class
		function() {
		
			$(this).children('div').fadeOut('1000');	
		
	}).click (function () {
	
		//Add selected class if user clicked on it
		$(this).addClass('selected');
		
	});

});
/* Purchase Now button fadein effect ends */