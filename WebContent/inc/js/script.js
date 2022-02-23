$(document).ready(function(){
	$(".notif-warning .close-container").click(function(){
		$(".notif-warning").removeClass("show");
		$(".notif-warning").addClass("hide");
	});
	
	$("#user-connect").click(function(){
		
		gifLoader("#user-connect","Se connecter");
		
		var data = new Object();
		data.email = $("input[name=email]").val();
		data.password = $("input[name=password]").val();
		
		$.post("/compte/connect",data,function(data){
			if(data.type == "success"){
				if(data.uri != null){
					$(location).attr("href",data.uri);
				}else{
					location.reload();
				}
			}else if(data.msg != "ok"){
				createErrorNotif(data.msg,5,"top-left")
			}
		},"json")
		
	});
	
	
	$(".add-favoris").click(function(){
		var data = new Object();
		data.formationId = $(this).val();
		
		//var component = $(this);
		//var componentParent = $(this).parent();
		//component.attr("disabled","disabled")
		
		$.post("/add-favoris",data,function(data){
			if(data.type == "session"){
				//Envoyer une notifaction d'abord ou crée une page de connexion
				$(location).attr("href","/compte/login")
			}else if(data.type == "success"){
				location.reload();
				//
				//component.removeClass("all-favoris-add add-favoris");
				//component.addClass("all-favoris-remove remove-favoris");
				//component.remove();
				//componentParent.prepend("<button title=\"Supprimer à mes favoris\" class=\"all-favoris-remove remove-favoris\" value=\""+data.favorisId+"\"><i class=\"bi bi-heart-fill\"></i></button>")
				//component.removeAttr("disabled")
				//location.reload();
			}
			
		},"json")
		
	})
	
	$(".remove-favoris").click(function(){
		var component = $(this);
		component.attr("disabled","disabled")
		$.post("/remove-favoris",{id : $(this).val()},function(data){
			if(data.type == "session"){
				//Envoyer une notifaction d'abord ou crée une page de connexion
				$(location).attr("href","/compte/login")
			}else if(data.type == "success"){
				location.reload();
				/*component.removeClass("all-favoris-remove remove-favoris")
				component.addClass("all-favoris-add add-favoris");
				component.removeAttr("disabled")*/
				//location.reload();
			}
			
		},"json")
		
	})
	
	//Ajouter au panier
	$(".add-to-basket").click(function(){
		
		var component = $(this);
		var formationId = $(this).val();
		component.attr("disabled","disabled");
		
		$.post("/add-to-basket",{id: formationId},function(data){
			if(data.type == "success"){
				location.reload();
				
			}else if(data.type == "session"){
				$(location).attr("href","/compte/login")
			}else{
				component.removeAttr("disabled");
				
				if(data.id != null){
					createErrorNotif(data.id,5,"top-left")
				 }
				
				 if(data.msg != null){
					createErrorNotif(data.msg,5,"top-left")
				 }	
			
			}
		},"json")
	})
	
	$(".go-to-basket-home").click(function(){
		$(location).attr("href","/panier")
	})
	
	$("footer .col-md-4").click(function(){
		$(this).toggleClass("openf")
	})
	
	$("#btn-facebook").click(function(){

			FB.getLoginStatus(function(response) {
			   if(response.status === "connected"){
				   facebookUser();
			   }else{
				   FB.login(function(response) {
						 if(response.status === "connected"){
							 facebookUser();
						 }
					}, {scope: 'public_profile,email'});
			   }
			});
		})
		
		$("#btn-facebook-nav").click(function(){

			FB.getLoginStatus(function(response) {
			   if(response.status === "connected"){
				   facebookUser();
			   }else{
				   FB.login(function(response) {
						 if(response.status === "connected"){
							 facebookUser();
						 }
					}, {scope: 'public_profile,email'});
			   }
			});
		})
	
	function facebookUser(){
			FB.api('/me', 'GET',{"fields":"id,first_name,last_name,email,picture"},
					  function(response) {
					    
					     var data = new Object();
					     data.id = response.id;
					     data.nom = response.last_name;
					     data.prenoms = response.first_name;
					     data.email = response.email;
					     data.photo = response.picture.data.url;
					     
					     $.post("/facebook-connexion",data,function(data){
					    	 
					    	 if(data.type == "success"){
									if(data.uri != null){
										$(location).attr("href",data.uri);
									}else{
										location.reload();
									}
								}else if(data.msg != "ok"){
									createErrorNotif(data.msg,5,"top-left")
								}
					    	 
					     },"json")
					  }
					);
		}	
	
});