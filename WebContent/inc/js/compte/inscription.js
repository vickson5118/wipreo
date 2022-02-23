$(document).ready(function(){
	
	validerEntree("#btn-inscription-valide")
	
	inputValidation("#nom","nom",2,100,true);
	inputValidation("#prenoms","prenoms",2,250,true);
	emailInputValidation("#email");
	
	$("#btn-inscription-valide").click(function(){
		
		gifLoader("#btn-inscription-valide", "S'incrire")
		$("#btn-inscription-valide").attr("disabled","disabled")
		
		var data = new Object();
		data.nom = $("input[name=nom]").val();
		data.prenoms = $("input[name=prenoms]").val();
		data.email = $("input[name=email]").val();
		
		$.post("/compte/inscription",data,function(data){
			if(data.type === "success"){
				createSuccessNotif("Un mail a été envoyé a l'adresse mail spécifié. Si vous ne le trouvez pas vérifier dans vos spams.",5,"top-right")
				setTimeout(function(){
					$(location).attr("href","/compte/preferences")
				},5000)
			}else{
				$("#btn-inscription-valide").removeAttr("disabled")
				
				if(data.nom != null){
					$("#nom").addClass("is-invalid")
					$("#nom").parent().find(".error").text(data.nom)
				}else{
					$("#nom").removeClass("is-invalid").addClass("is-valid")
					$("#nom").parent().find(".error").text("")
				}
				
				if(data.prenoms != null){
					$("#prenoms").addClass("is-invalid")
					$("#prenoms").parent().find(".error").text(data.prenoms)
				}else{
					$("#prenoms").removeClass("is-invalid").addClass("is-valid")
					$("#prenoms").parent().find(".error").text("")	
				}
				
				if(data.email != null){
					$("#email").addClass("is-invalid")
					$("#email").parent().find(".error").text(data.email)
				}else{
					$("#email").removeClass("is-invalid").addClass("is-valid")
					$("#email").parent().find(".error").text("")
				}
				
				if(data.emailSend != null){
					createErrorNotif(data.emailSend,5,"top-right")
				}
			}
		},"json")
	})
	
})