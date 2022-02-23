$(document).ready(function(){
	
	$(document).click(function(event){
		
		if(!$(event.target).closest(".espace-client-header").length){
			$("#container-connexion").removeClass("open-connexion-box")
			$("#container-connexion").css({"visibility" : "hidden"});
			$(".espace-client-header span").css({"background-color" : "white","color":"black"});
		}
		
		if(!$(event.target).closest(".espace-client-header").length){
			$(".user-connect-container").removeClass("open-connect-box")
			$(".user-connect-container").css({"visibility" : "hidden"});
			$(".espace-client-header span").css({"background-color" : "white","color":"black"});
		}
		
		
		
	})
	
	/*au survol de l'espace client*/
	$(".espace-client-header span").click(function(){
		if($("#container-connexion").hasClass("open-connexion-box")){
			$("#container-connexion").removeClass("open-connexion-box")
			$("#container-connexion").css({"visibility" : "hidden"});
			$(".espace-client-header span").css({"background-color" : "white","color":"black"});
		}else{
			$("#container-connexion").addClass("open-connexion-box")
			$("#container-connexion").css({"visibility" : "visible"});
			$(".espace-client-header span").css({"background-color" : "black","color":"white"});
		}
	});
	
	$(".espace-client-header span").click(function(){
		if($(".user-connect-container").hasClass("open-connect-box")){
			$(".user-connect-container").removeClass("open-connect-box")
			$(".user-connect-container").css({"visibility" : "hidden"});
			$(".espace-client-header span").css({"background-color" : "white","color":"black"});
		}else{
			$(".user-connect-container").addClass("open-connect-box")
			$(".user-connect-container").css({"visibility" : "visible"});
			$(".espace-client-header span").css({"background-color" : "black","color":"white"});
		}
	});
	
	$(".nav-domaines>a").click(function(event){
		event.preventDefault();
		$(".nav-domaines").toggleClass("smenu-active")
	})
	
	$(".checkbtn").click(function(event){
		$(this).toggleClass("open")
		$(".nav").toggleClass("open-nav");
	})
	
	$(".menu-profil-picture").click(function(){
		$(".res-user-connect-container").toggleClass("open-user-connect");
	})
	
	

});