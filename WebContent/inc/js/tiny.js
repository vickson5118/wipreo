

$(document).ready(function() {
	/*tinymce.init({
		selector: '#description',
		language: "fr_FR",
		plugins: 'save lists',
		menubar: false,
		toolbar: 'save | undo redo | bold italic underline | bullist',
		save_onsavecallback: function() {
			$.post("/update-formation-description", { description: tinyMCE.get('description').getContent() })
		}
		/*setup: function (editor) {
		  editor.on('blur', function () {
			$.post("/update-formation-description",{description: tinyMCE.get( 'description' ).getContent()});
		  });
		}
	});*/

	tinymce.init({
		selector: '#prerequis',
		language: "fr_FR",
		plugins: 'save lists',
		menubar: false,
		toolbar: 'save | undo redo | bold italic underline | bullist',
		save_onsavecallback: function() {
			$.post("/update-formation-prerequis", { prerequis: tinyMCE.get('prerequis').getContent() })
		}
		/*setup: function (editor) {
		  editor.on('blur', function () {
			 $.post("/update-formation-prerequis",{prerequis: tinyMCE.get( 'prerequis' ).getContent()});
		  });
		}*/
	});

	tinymce.init({
		selector: '#pour-qui',
		language: "fr_FR",
		plugins: 'save lists',
		menubar: false,
		toolbar: 'save | undo redo | bold italic underline | bullist',
		save_onsavecallback: function() {
			$.post("/update-formation-pour-qui", { pourQui: tinyMCE.get('pour-qui').getContent() })
		}
		/*setup: function (editor) {
		  editor.on('blur', function () {
			$.post("/update-formation-pour-qui",{pourQui: tinyMCE.get( 'pour-qui' ).getContent()});
		  });
		}*/
	});

	tinymce.init({
		selector: '#objectifs',
		language: "fr_FR",
		plugins: 'save lists',
		menubar: false,
		toolbar: 'save | undo redo | bold italic underline | bullist',
		save_onsavecallback: function() {
			$.post("/update-formation-objectifs", { objectifs: tinyMCE.get('objectifs').getContent() })
		}
		/*setup: function (editor) {
		  editor.on('blur', function () {
			$.post("/update-formation-objectifs",{objectifs: tinyMCE.get( 'objectifs' ).getContent()});
		  });
		}*/
	});

	tinymce.init({
		selector: '#points-cles',
		language: "fr_FR",
		plugins: 'save lists',
		menubar: false,
		toolbar: 'save | undo redo | bold italic underline | bullist',
		save_onsavecallback: function() {
			$.post("/update-formation-points-cles", { pointsCles: tinyMCE.get('points-cles').getContent() })
		}
		/*setup: function (editor) {
		  editor.on('blur', function () {
			$.post("/update-formation-points-cles",{pointsCles: tinyMCE.get( 'points-cles' ).getContent()});
		  });
		}*/
	});
})

