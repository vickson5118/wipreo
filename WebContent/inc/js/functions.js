
//faire apparaitre un gif lorsqu'on click sur un btn'
function gifLoader(selecteur, texteBtn) {

	$(document).ajaxStart(function() {
		var height = $(selecteur).css("height");
		var width = $(selecteur).css("width");
		$(selecteur).text("");
		$(selecteur).css({
			"height": height,
			"width": width,
			"backgroundImage": "url(/inc/images/gif/loader.svg)",
			"backgroundRepeat": "no-repeat",
			"backgroundPosition": "center"
		});
	});

	$(document).ajaxStop(function() {
		$(selecteur).text(texteBtn);
		$(selecteur).css({
			"backgroundImage": "none"
		});
	});
}

function validerEntree(selecteur) {
	$(window).keydown(function(e) {
		if (e.keyCode == 13) {
			$(selecteur).trigger("click");
		}
	});
}

function validerEntreeWithContainer(container, selecteur) {
	$(container).keydown(function(e) {
		if (e.keyCode == 13) {
			$(selecteur).trigger("click");
		}
	});
}

function createSuccessNotif(message,time,position){
	alertify.set("notifier","position",position)
	alertify.success(message,time);
}

function createErrorNotif(message,time,position){
	alertify.set("notifier","position",position)
	alertify.error(message,time);
}

function createInfoNotif(message,time,position){
	alertify.set("notifier","position",position)
	alertify.notify(message,time);
}

function inputValidation(selector,champ,minLength,maxLength,required){
	$(selector).keyup(function(){
		const texteLength = $(this).val().trim().length;
		if( ((!required && texteLength > 0) && texteLength < minLength) || (required && texteLength < minLength)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le champ "+champ+" ne peut être inférieur à "+minLength+" caractères.")
		}else if(texteLength > maxLength){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le champ "+champ+" ne peut être supérieur à "+maxLength+" caractères.")
		}else{
			$(this).removeClass("is-invalid").addClass("is-valid")
			$(this).parent().find(".error").text("")
		}
	})
}

function emailInputValidation(selector){
	$(selector).keyup(function(){
		if(!$(this).val().match(/^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le format de l'adresse mail n'est pas correct.")
		}else{
			$(this).removeClass("is-invalid").addClass("is-valid")
			$(this).parent().find(".error").text("")
		}
	})
}

function passwordInputValidation(selector){
	$(selector).keyup(function(){
		if($(this).val().length < 8){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le mot de passe doit être supérieur à 8 caractères.")
		}else if(!$(this).val().match(/[a-z]+/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le mot de passe doit contenir au moins un caratère minuscule.")
		}else if(!$(this).val().match(/[A-Z]+/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le mot de passe doit contenir au moins un caratère majuscule.")
		}else if(!$(this).val().match(/[0-9]+/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le mot de passe doit contenir au moins un chiffre.")
		}else if(!$(this).val().match(/[@~&*+°|#^ù%µ$<>!:;=-]+/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le mot de passe doit contenir au moins un caratère suivant : @~&*+°|#^ù%µ$<>!:;=-")
		}else{
			$(this).removeClass("is-invalid").addClass("is-valid")
			$(this).parent().find(".error").text("")
		}
	})
}

function telInputValidation(selector){
	$(selector).keyup(function(){
		 if($(this).val().length > 0 && !$(this).val().match(/^([0-9]{2}[. -]?){4}[0-9]{2}$/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le format du numéro téléphonique n'est pas correct.")
		}else{
			$(this).removeClass("is-invalid").addClass("is-valid")
			$(this).parent().find(".error").text("")
		}
	})
}

function dateInputValidation(selector){
	$(selector).keyup(function(){
		 if($(this).val().length > 0 && !$(this).val().match(/^[0-9]{2}-[0-9]{2}-[0-9]{4}$/)){
			$(this).addClass("is-invalid")
			$(this).parent().find(".error").text("Le format de la date n'est pas correct.")
		}else{
			$(this).removeClass("is-invalid").addClass("is-valid")
			$(this).parent().find(".error").text("")
		}
	})
}

