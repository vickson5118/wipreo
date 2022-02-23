$(document).ready(function(){
	
	//validerEntree("#send-message")
	
	inputValidation("#nom","nom",2,100,true);
	inputValidation("#prenoms","prenoms",2,250,false);
	telInputValidation("#telephone");
	emailInputValidation("#contact-email");
	inputValidation("#objet","objet",10,500,true);
	inputValidation("#message","message",30,1500,true);
	
	
	$("#send-message").click(function(){
		
		gifLoader("#send-message","Envoyer le message")
		
		var data = new Object();
		data.nom = $("input[name=nom]").val();
		data.prenoms = $("input[name=prenoms]").val();
		data.telephone = $("input[name=telephone]").val();
		data.email = $("input[name=contact-email]").val();
		data.objet = $("input[name=objet]").val();
		data.message = $("textarea[name=message]").val();
		
		$.post("/contactez-nous",data,function(data){
			if(data.type == "success"){
				createSuccessNotif("Vous message a bien été envoyé. Nous vous repondrons sous peu.",5,"top-right")
				setTimeout(function(){
					$(location).attr("href","/"); 
				}, 5000);
			}else{
				
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
					$("#contact-email").addClass("is-invalid")
					$("#contact-email").parent().find(".error").text(data.email)
				}else{
					$("#contact-email").removeClass("is-invalid").addClass("is-valid")
					$("#contact-email").parent().find(".error").text("")
				}
				
				if(data.telephone != null){
					$("#telephone").addClass("is-invalid")
					$("#telephone").parent().find(".error").text(data.telephone)
				}else{
					$("#telephone").removeClass("is-invalid").addClass("is-valid")
					$("#telephone").parent().find(".error").text("")
				}
				
				if(data.objet != null){
					$("#objet").addClass("is-invalid")
					$("#objet").parent().find(".error").text(data.objet)
				}else{
					$("#objet").removeClass("is-invalid").addClass("is-valid")
					$("#objet").parent().find(".error").text("")	
				}
				
				if(data.message != null){
					$("#message").removeClass("is-invalid").addClass("is-invalid")
					$("#message").parent().find(".error").text(data.message)
				}else{
					$("#message").addClass("is-valid")
					$("#message").parent().find(".error").text("")
				}
				
			}
		},"json")
	});
})