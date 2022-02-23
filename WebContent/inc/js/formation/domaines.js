$(document).ready(function() {
	
	//$(".page").css("background-color","#f07b16")
	
	
	$(".link-page").click(function(event){
		event.preventDefault();
		
		var component = $(this);
		
		var page = $(this).val().split("-")[0];
		var domaineTitreUrl = $(this).val().split("-")[1];
		
		$.get("/get-page",{page,domaineTitreUrl},function(data){
			
			$(".pagination button").css("background-color","#808080")
			component.css("background-color","#f07b16")
			
			$("#other-formations").html("")
			for(var i = 0 ; i<data.length; i++){
				var card = "<div class=\"col-md-3 formation-item\">";
				card += "<div class=\"card\">";
				card += "<img src="+data[i].illustration+" class=\"card-img-top\" alt=\""+data[i].titre+"\">";
				card += "<div class=\"card-body\">";
				card += "<h1 class=\"card-title\">";
				card += "<a href=\"\"><span class=\"cours-title\">"+data[i].titre+"</span></a>";
				card += "</h1>";
				card += "<div class=\"auteur\">";
				card += "<span class=\"auteur-img\"></span><span>"+data[i].auteur.prenoms+" "+data[i].auteur.nom+"</span>";
				card += "</div>";
				card += "<div class=\"formation-extra-container\">";
				card += "<span style=\"font-size: 14px;\">Note : </span>";
				card += "<span class=\"frating-number\">"+data[i].rating+" / 5</span>";
				card += "</div>";
				card += "<div class=\"formation-prix\">";
				card += "<b>"+data[i].prix+" FCFA</b>";
				card += "</div>";
				card += "</div>";
				card += "</div>";
				card += "</div>";

				$("#other-formations").append(card);
			}
			
		},"json")
		
	});
	
	//carrousel de cours populaire
	$('.slider-cours-populaire').slick({
		dots: false,
		infinite: true,
		speed: 500,
		slidesToShow: 4,
		slidesToScroll: 1,
		prevArrow: '.cours-populaires-prev',
		nextArrow: '.cours-populaires-next',
		/*autoplay: true,
		autoplaySpeed: 10000,*/
		responsive: [
			{
				breakpoint: 1200,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 1,
					infinite: true,
					dots: false
				}
			},
			{
				breakpoint: 992,
				settings: {
					slidesToShow: 2,
					slidesToScroll: 1,
					centerMode: true,
					 variableWidth: true
				}
			},
			{
				breakpoint: 670,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1,
					centerMode: true,
					variableWidth: true
				}
			}
			// You can unslick at a given breakpoint now by adding:
			// settings: "unslick"
			// instead of a settings object
		]
	});
	
	//carrousel des derniers posts
	$('.slider-derniers-posts').slick({
		infinite: true,
		dots: false,
		speed: 500,
		slidesToShow: 1,
		slidesToScroll: 1,
		prevArrow: '.derniers-posts-prev',
		nextArrow: '.derniers-posts-next',
		/*autoplay: true,
		autoplaySpeed: 6000,*/
		responsive: [
			{
				breakpoint: 992,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1,
					autoplay: true,
					autoplaySpeed: 5000,
					 centerMode: true,
					 variableWidth: true
				}
			},
			/*{
				breakpoint: 839,
				settings: {
					centerMode: true,
					 variableWidth: true,
					slidesToShow: 1,
					slidesToScroll: 1,
				}
			},*/
			/*{
				breakpoint: 460,
				settings: {
					centerMode: false,
					dots: false,
					infinite: true,
					slidesToShow: 1,
					slidesToScroll: 1,
					/*autoplay: true,
					autoplaySpeed: 5000*/
				//}
			//}
		]
	});
	
	
	//carrousel de themes populaires
	$('.slider-domaines-populaires').slick({
		dots: false,
		infinite: false,
		speed: 500,
		slidesToShow: 4,
		slidesToScroll: 1,
		prevArrow: '.domaines-populaires-prev',
		nextArrow: '.domaines-populaires-next',
		responsive: [
			{
				breakpoint: 1200,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 1,
					infinite: false,
					dots: false
				}
			},
			{
				breakpoint: 992,
				settings: {
					slidesToShow: 2,
					slidesToScroll: 1
				}
			},
			{
				breakpoint: 580,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1
				}
			}
		]
	});

	
	//carrousel des derniers posts
	$('.slider-formateurs-populaires').slick({
		dots: false,
		infinite: false,
		speed: 500,
		slidesToShow: 4,
		slidesToScroll: 1,
		prevArrow: '.formateurs-prev',
		nextArrow: '.formateurs-next',
		/*autoplay: true,
		autoplaySpeed: 10000,*/
		responsive: [
			{
				breakpoint: 1200,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 1,
					infinite: false,
					dots: false
				}
			},
			{
				breakpoint: 992,
				settings: {
					slidesToShow: 2,
					slidesToScroll: 1
				}
			},
			{
				breakpoint: 580,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1
				}
			}
	
		]
	});


});