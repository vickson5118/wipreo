$(document).ready(function() {

	$("#dateNaissance").datepicker({
		monthNames: ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
			"Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"],
		monthNamesShort: ["Janv.", "Févr.", "Mars", "Avr.", "Mai", "Juin",
			"Juil.", "Août", "Sept.", "Oct.", "Nov.", "Déc."],
		dayNames: ["Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"],
		dayNamesShort: ["Dim.", "Lun.", "Mar.", "Mer.", "Jeu.", "Ven.", "Sam."],
		dayNamesMin: ["D", "L", "M", "M", "J", "V", "S"],
		prevText: "Précédent",
		nextText: "Suivant",
		currentText: "Aujourd'hui",
		dateFormat: "dd/mm/yy",
		yearRange: "1900:c",
		changeMonth: true,
		changeYear: true
	});

	$("#btn-confirm-add-commentaire").click(function() {
		gifLoader("#btn-confirm-add-commentaire", "Ajouter le commentaire")
		$("#btn-confirm-add-commentaire").attr("disabled","disabled")

		var data = new Object();
		data.rating = $(".rating").rate("getValue")
		data.avis = $("#avis").val();

		$.post("/add-commentaire", data, function(data) {
			if (data.type === "success") {
				createSuccessNotif("Votre avis a été ajouté avec success.", 3, "top-right")
				setTimeout(function() {
					location.reload()
				}, 3000)
			} else if (data.type === "session") {
				$(location).attr("href", "/compte/login")
			} else {
				
				$("#btn-confirm-add-commentaire").removeAttr("disabled")
				
				if (data.rating != null) {
					$(".rating").parent().find(".error").text(data.rating)
				}


				if (data.avis != null) {
					$("#avis").addClass("is-invalid")
					$("#avis").parent().find(".error").text(data.avis)
				} else {
					$("#avis").removeClass("is-invalid").addClass("is-valid")
					$("#avis").parent().find(".error").text("")
				}

			}
		}, "json")
	});

	var rateOptions = {
		max_value: 5,
		initial_value: 0,
		step_size: 0.5
	}
	$(".rating").rate(rateOptions);


	$(".lesson-source").click(function() {
		
		var link = $(this);
	
		var id = link.attr("href").substring(link.attr("href").lastIndexOf("/") + 1)

		$.post("/lesson-view", { id }, function(data) {
			if (data.type == "success") {
				link.css("color", "#2e8944")
				link.addClass("lesson-view")
			}
		}, "json");


	})

	$("#btn-confirm-terminate-formation").click(function() {
		
		gifLoader("#btn-confirm-terminate-formation", "Terminer la formation")
		$("#btn-confirm-terminate-formation").attr("disabled","disabled")
		
		var data = new Object();
		data.prenoms = $("#prenoms").val();
		data.nom = $("#nom").val();
		data.date = $("#dateNaissance").val();

		$.post("/formation-terminated", data, function(data) {
			if (data.type == "session") {
				$(location).attr("href", "/compte/login")
			} else if(data.msg != null) {
				
				$("#btn-confirm-terminate-formation").removeAttr("disabled")
				
				createErrorNotif(data.msg, 4, "top-right")
				setTimeout(function(){location.reload()},4000)
			}else{
				if(data.formationEchec != null){
					createErrorNotif(data.formationEchec, 5, "top-right")
				}
				
				if(data.formationSuccess != null){
					createSuccessNotif(data.formationSuccess, 5, "top-right")
				}

				setTimeout(function(){location.reload()},5000)
			}
		}, "json")
	})


})