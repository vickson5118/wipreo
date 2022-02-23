$(document).ready(function(){
	
	$("#date-naissance").datepicker({
		monthNames: [ "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
			"Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" ],
		monthNamesShort: [ "Janv.", "Févr.", "Mars", "Avr.", "Mai", "Juin",
			"Juil.", "Août", "Sept.", "Oct.", "Nov.", "Déc." ],
		dayNames: [ "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" ],
		dayNamesShort: [ "Dim.", "Lun.", "Mar.", "Mer.", "Jeu.", "Ven.", "Sam." ],
		dayNamesMin: [ "D","L","M","M","J","V","S" ],
		prevText: "Précédent",
		nextText: "Suivant",
		currentText: "Aujourd'hui",
		dateFormat:"dd-mm-yy",
		yearRange:"1900:c",
		changeMonth:true,
		changeYear:true
	});
	
	
	$("#profilFile").change(function() {		
		$("#profil-picture-form").trigger("submit");
	});
	
	$("#profil-picture-form").submit(function(event){
		event.preventDefault();
		
		/*$("#btn-valide-presentation-info").attr("disabled","disabled")
		gifLoader("#btn-valide-presentation-info","Valider les informations")*/

		$.ajax({
			url: "/update-profil-picture",
			method: "POST",
			data: new FormData(this),
			contentType: false,
			processData: false,
			dataType: "json",
			success: function(data) {
				if (data.type == "success") {
					location.reload();
				} else if(data.type == "session"){
					$(location).attr("href","/compte/login")
				} 
			}

		});
		
	})
	
	inputValidation("#nom","nom",2,100,true)
	inputValidation("#prenoms","prenoms",2,250,true)
	telInputValidation("#telephone")
	dateInputValidation("#date-naissance")
	
	
	$("#update-user-infos").click(function(){
		
		gifLoader("#update-user-infos","Valider la modification");
		
		var data = new Object();
		data.nom = $("#nom").val();
		data.prenoms = $("#prenoms").val();
		data.telephone = $("#telephone").val();
		data.dateNaissance = $("#date-naissance").val();
		data.sexe = $("#sexe").val();
		data.pays = $("#pays").val();
		
		$.post("/update-user-infos",data,function(data){
			if(data.type == "success"){
				location.reload();
			}else if(data.type == "session"){
				$(location).attr("href","/compte/login")
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
				
				if(data.dateNaissance != null){
					$("#date-naissance").addClass("is-invalid")
					$("#date-naissance").parent().find(".error").text(data.dateNaissance)
				}else{
					$("#date-naissance").removeClass("is-invalid").addClass("is-valid")
					$("#date-naissance").parent().find(".error").text("")
				}
				
				if(data.telephone != null){
					$("#telephone").addClass("is-invalid")
					$("#telephone").parent().find(".error").text(data.telephone)
				}else{
					$("#telephone").removeClass("is-invalid").addClass("is-valid")
					$("#telephone").parent().find(".error").text("")
				}
				
				if(data.sexe != null){
					$("#sexe").addClass("is-invalid")
					$("#sexe").parent().find(".error").text(data.sexe)
				}else{
					$("#sexe").removeClass("is-invalid").addClass("is-valid")
					$("#sexe").parent().find(".error").text("")	
				}
				
				if(data.pays != null){
					$("#pays").removeClass("is-invalid").addClass("is-invalid")
					$("#pays").parent().find(".error").text(data.pays)
				}else{
					$("#pays").addClass("is-valid")
					$("#pays").parent().find(".error").text("")
				}
			}
		},"json")
		
	})

})