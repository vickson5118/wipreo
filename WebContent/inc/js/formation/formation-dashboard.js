$(document).ready(function(){
	
	var rateOptions = {
		readonly: true
	}
	$(".rating").rate(rateOptions);
	
	/**Necessaire pour faire apparaitre l'ancre des que l'on recharge la page */
	var scrollTop = $(document).scrollTop();
	if(screen.width > 767){
		
		if(scrollTop >= 400){
		$(".ancre-container").css("display","block");
	}else{
		$(".ancre-container").css("display","none");
	}
	
	$(document).scroll(function(){
		var scrollTop = $(document).scrollTop();
		if(scrollTop >= 400){
			$(".ancre-container").css("display","block");
		}else{
			$(".ancre-container").css("display","none");
		}
	})
		
	}
	
});