$(document).ready(function(){
	
	emailInputValidation("#email")
	emailInputValidation("#new-email")
	
	
	$("#btn-update-email").click(function(){
		
		gifLoader("#btn-change-email","Valider la nouvelle adresse mail");
		
		var data = new Object();
		data.email = $("#email").val();
		data.newEmail = $("#new-email").val();
		
		$.post("/update-user-email",data,function(data){
			if(data.type == "success"){
				location.reload();
			}else if(data.type == "session"){
				$(location).attr("href","/compte/login")
			}else{
				
				if(data.email != null){
					$("#email").addClass("is-invalid")
					$("#email").parent().find(".error").text(data.email)
				}else{
					$("#email").removeClass("is-invalid").addClass("is-valid")
					$("#email").parent().find(".error").text("")
				}
				
				if(data.newEmail != null){
					$("#new-email").addClass("is-invalid")
					$("#new-email").parent().find(".error").text(data.newEmail)
				}else{
					$("#new-email").removeClass("is-invalid").addClass("is-valid")
					$("#new-email").parent().find(".error").text("")
				}
			}
		},"json")
		
	})
	
	passwordInputValidation("#new-password")
	
	$("#repeat-new-password").keyup(function(){
		if($("#new-password").val() != $("#repeat-new-password").val()){
			$("#repeat-new-password").addClass("is-invalid")
			$("#repeat-new-password").parent().find(".error").text("Les mots de passe ne sont pas identiques.")
		}else{
			$("#repeat-new-password").removeClass("is-invalid").addClass("is-valid")
			$("#repeat-new-password").parent().find(".error").text("")
		}
	})
	
	$("#btn-update-password").click(function(){
		
		gifLoader("#btn-update-password","Rédéfinir le mot de passe");
		
		var data = new Object();
		data.password = $("#new-password").val();
		data.repeatPassword = $("#repeat-new-password").val();
		
		$.post("/compte/redefine-password",data,function(data){
			if(data.type == "success"){
				location.reload();
			}else if(data.type == "session"){
				$(location).attr("href","/compte/login")
			}else{
				
				if(data.password != null){
					$("#password").addClass("is-invalid")
					$("#password").parent().find(".error").text(data.password)
				}else{
					$("#password").removeClass("is-invalid").addClass("is-valid")
					$("#password").parent().find(".error").text("")	
				}
				
				if(data.repeatPassword != null){
					$("#repeat-new-password").addClass("is-invalid")
					$("#repeat-new-password").parent().find(".error").text(data.repeatPassword)
				}else{
					$("#repeat-new-password").removeClass("is-invalid").addClass("is-valid")
					$("#repeat-new-password").parent().find(".error").text("")	
				}
				
			}
		},"json")
		
	})
	
	
})