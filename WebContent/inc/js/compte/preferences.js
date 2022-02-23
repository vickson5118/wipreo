$(document).ready(function(){
	
	/*$(".btn-domaine-type-valide").click(function(){
		$(location).attr("href","/");
	})*/

	/*Ajouter un objectif à l'utilisateur*/
	$(".objectif-card").click(function(){
		
		var component = $(this);
		
		$.post("/add-objectif",{id: $(this).val()},function(data){
			
			if(data.type == "success"){
				$(".objectif-card").removeClass("objectif-actif")
				component.addClass("objectif-actif");
			}else{
				createErrorNotif(data.id,3,"top-right")
			}
			
		},"json")
		
	})
	
	/*Ajouter un domaine favoris*/
	$(".domaine-type").click(function(){
		
		var component = $(this);
		
		$.post("/add-domaine-favoris",{id: $(this).val()},function(data){
			
			if(data.type == "success"){
				$(".domaine-type").removeClass("domaine-actif")
				component.addClass("domaine-actif");
			}else{
				createErrorNotif(data.id,3,"top-right")
			}
			
		},"json")
		
	})
	
})