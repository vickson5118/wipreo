$(document).ready(function(){
	
	emailInputValidation("#reset-pass-email");
	
	$("#btn-reset-pass-valide").click(function(){
		
		gifLoader("#btn-reset-pass-valide","Reinitialiser le mot de passe")
		
		$.post("/compte/reset-password",{email: $("#reset-pass-email").val()},function(data){
			if(data.type == "success"){
				$(location).attr("href","/");
			} else{
				
				if(data.email != null){
					$("#reset-pass-email").addClass("is-invalid")
					$("#reset-pass-email").parent().find(".error").text(data.email)
				}else{
					$("#reset-pass-email").removeClass("is-invalid").addClass("is-valid")
					$("#reset-pass-email").parent().find(".error").text("")
				}
				
			}
		},"json")
		
	})
	
})