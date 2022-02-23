$(document).ready(function(){
	
	inputValidation("#fonction","fonction",5,50,false)
	inputValidation("#site","site",5,250,false)
	inputValidation("#facebook","facebook",5,250,false)
	inputValidation("#twitter","twitter",5,250,false)
	inputValidation("#youtube","youtube",5,250,false)
	inputValidation("#linkedin","linkedin",5,250,false)
	inputValidation("#biographie","biographie",10,1000,false)
	
	$("#update-instructeur-infos").click(function(){
		
		gifLoader("#update-instructeur-infos","Valider les informations");
		
		var data = new Object();
		data.fonction = $("#fonction").val();
		data.site = $("#site").val();
		data.facebook = $("#facebook").val();
		data.twitter = $("#twitter").val();
		data.linkedin = $("#linkedin").val();
		data.youtube = $("#youtube").val();
		data.biographie = $("#biographie").val();
		
		$.post("/membre/profil/instructeur",data,function(data){
			if(data.type == "success"){
				createSuccessNotif("Votre profil a été mise à jour.",2,"top-right")
				setTimeout(function(){location.reload();},2000)
			}else if(data.type == "session"){
				$(location).attr("href","/compte/login");
			}else{
				
				if(data.fonction != null){
					$("#fonction").addClass("is-invalid")
					$("#fonction").parent().find(".error").text(data.fonction)
				}else{
					$("#fonction").removeClass("is-invalid").addClass("is-valid")
					$("#fonction").parent().find(".error").text("")
				}
				
				if(data.site != null){
					$("#site").addClass("is-invalid")
					$("#site").parent().find(".error").text(data.site)
				}else{
					$("#site").removeClass("is-invalid").addClass("is-valid")
					$("#site").parent().find(".error").text("")	
				}
				
				if(data.facebook != null){
					$("#facebook").addClass("is-invalid")
					$("#facebook").parent().find(".error").text(data.facebook)
				}else{
					$("#facebook").removeClass("is-invalid").addClass("is-valid")
					$("#facebook").parent().find(".error").text("")
				}
				
				if(data.twitter != null){
					$("#twitter").addClass("is-invalid")
					$("#twitter").parent().find(".error").text(data.twitter)
				}else{
					$("#twitter").removeClass("is-invalid").addClass("is-valid")
					$("#twitter").parent().find(".error").text("")
				}
				
				if(data.youtube != null){
					$("#youtube").addClass("is-invalid")
					$("#youtube").parent().find(".error").text(data.youtube)
				}else{
					$("#youtube").removeClass("is-invalid").addClass("is-valid")
					$("#youtube").parent().find(".error").text("")	
				}
				
				if(data.linkedin != null){
					$("#linkedin").removeClass("is-invalid").addClass("is-invalid")
					$("#linkedin").parent().find(".error").text(data.linkedin)
				}else{
					$("#linkedin").addClass("is-valid")
					$("#linkedin").parent().find(".error").text("")
				}
				
				if(data.biographie != null){
					$("#biographie").removeClass("is-invalid").addClass("is-invalid")
					$("#biographie").parent().find(".error").text(data.biographie)
				}else{
					$("#biographie").addClass("is-valid")
					$("#biographie").parent().find(".error").text("")
				}
				
			}
		},"json")
		
		
	})
	
})