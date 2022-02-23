$(document).ready(function(){
	
	//passwordInputValidation("#password")
	paswordLevelColor("#password")
	
	$("#repeat-password").keyup(function(){
		if($("#password").val() != $(this).val()){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Les mots de passe ne sont pas identiques.")
		}else{
			$("#repeat-password").removeClass("is-invalid").addClass("is-valid")
			$(this).parent().find(".error").text("")
		}
	})
	
	
	$("#redefinir-password-form").submit(function(event){
		event.preventDefault()
		
		gifLoader("#btn-valide-change-password", "Valider le mot de passe")
		
		var data = new Object();
		data.password = $("#password").val();
		data.repeatPassword = $("#repeat-password").val();
		$.post("/compte/redefine-password",data,function(data) {
				if (data.type == "success") {
					$(location).attr("href","/")
				}else if(data.type == "session"){
					$(location).attr("href","/compte/login")
				} else {
					
					if(data.password != null){
						$("#password").addClass("is-invalid")
						$("#password").parent().find(".error").text(data.password)
					}else{
						$("#password").removeClass("is-invalid").addClass("is-valid")
						$("#password").parent().find(".error").text("")
					}
					
					if(data.repeatPassword != null){
						$("#repeat-password").addClass("is-invalid")
						$("#repeat-password").parent().find(".error").text(data.repeatPassword)
					}else{
						$("#repeat-password").removeClass("is-invalid").addClass("is-valid")
						$("#repeat-password").parent().find(".error").text("")
					}

				}
			},"json")
		
	})
	
	
	function paswordLevelColor(selector){
		var lengthFlag = true;
		var lowercaseFlag = true;
		var uppercaseFlag = true;
		var numberFlag = true;
		var metaCharFlag = true;
	$(selector).keyup(function(){
		if($(this).val().length >= 8){
			if(lengthFlag){
				$(".pass-security").first().addClass("pass-security-ok").removeClass("pass-security");
				lengthFlag=false;
			}
		}else if($(this).val().length < 8){
			if(!lengthFlag){
				$(".pass-security-ok").last().removeClass("pass-security-ok").addClass("pass-security");
				lengthFlag=true;
			}
		}

		if($(this).val().match(/[a-z]+/)){
			if(lowercaseFlag){
				$(".pass-security").first().addClass("pass-security-ok").removeClass("pass-security");
				lowercaseFlag=false;
			}
		}else if(!$(this).val().match(/[a-z]+/)){
			if(!lowercaseFlag){
				$(".pass-security-ok").last().removeClass("pass-security-ok").addClass("pass-security");
				lowercaseFlag=true;
			}
		}
		
		if($(this).val().match(/[A-Z]+/)){
			if(uppercaseFlag){
				$(".pass-security").first().addClass("pass-security-ok").removeClass("pass-security");
				uppercaseFlag=false;
			}
		}else if(!$(this).val().match(/[A-Z]+/)){
			if(!uppercaseFlag){
				$(".pass-security-ok").last().removeClass("pass-security-ok").addClass("pass-security");
				uppercaseFlag=true;
			}
		}
		
		if($(this).val().match(/[0-9]+/)){
			if(numberFlag){
				$(".pass-security").first().addClass("pass-security-ok").removeClass("pass-security");
				numberFlag=false;
			}
		}else if(!$(this).val().match(/[0-9]+/)){
			if(!numberFlag){
				$(".pass-security-ok").last().removeClass("pass-security-ok").addClass("pass-security");
				numberFlag=true;
			}
		}
		
		if($(this).val().match(/[@~&*+°|#^ù%µ$<>!:;=-]+/)){
			if(metaCharFlag){
				$(".pass-security").first().addClass("pass-security-ok").removeClass("pass-security");
				metaCharFlag=false;
			}
		}else if(!$(this).val().match(/[@~&*+°|#^ù%µ$<>!:;=-]+/)){
			if(!metaCharFlag){
				$(".pass-security-ok").last().removeClass("pass-security-ok").addClass("pass-security");
				metaCharFlag=true;
			}
		}
				
				
	})
	}
	
})