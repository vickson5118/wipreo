$(document).ready(function() {

	$('.carrousel-last-formations').slick({
		dots: true,
		infinite: true,
		speed: 2000,
		slidesToShow: 1,
		prevArrow: '.last-formation-prev',
		nextArrow: '.last-formation-next',
		fade: true,
		autoplay: true,
  		autoplaySpeed: 7000
	});

	/*les formations coups de coeur*/
	$('.slider-top-formations').slick({
		dots: false,
		infinite: true,
		speed: 500,
		slidesToShow: 4,
		slidesToScroll: 1,
		swipeToSlide: true,
		prevArrow: '.top-prev',
		nextArrow: '.top-next',
		responsive: [
			{
				breakpoint: 1200,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 1,
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
				breakpoint: 580,
				settings: {
					  slidesToShow: 1,
					  centerMode: true,
					  variableWidth: true
				}
			}
			// You can unslick at a given breakpoint now by adding:
			// settings: "unslick"
			// instead of a settings object
		]
	});

	/*top formations*/
	$('.slider-nouveautes').slick({
		dots: false,
		infinite: true,
		speed: 500,
		slidesToShow: 4,
		slidesToScroll: 1,
		prevArrow: '.nouveautes-prev',
		nextArrow: '.nouveautes-next',
		responsive: [
			{
				breakpoint: 1200,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 1,
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
				breakpoint: 580,
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

	$('.temoignage-slider').slick({
		dots: false,
		infinite: true,
		speed: 500,
		slidesToShow: 2,
		slidesToScroll: 2,
		prevArrow: '.temoignage-prev',
		nextArrow: '.temoignage-next',
		autoplay: true,
		autoplaySpeed: 2000,
		responsive: [
			{
				breakpoint: 1250,
				settings: {
					slidesToShow: 2,
					slidesToScroll: 1,
					infinite: false,
					dots: false
				}
			},
			{
				breakpoint: 600,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1
				}
			},
			{
				breakpoint: 480,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1
				}
			}
			// You can unslick at a given breakpoint now by adding:
			// settings: "unslick"
			// instead of a settings object
		]
	});

})