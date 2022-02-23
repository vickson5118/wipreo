$(document).ready(function() {

	CinetPay.setConfig({
		apikey: '10573704960a01e9fb67c90.06630299',
		site_id: 213335,
		notify_url: 'http://gehant.local:8080/cinetPayment',
		return_url: 'http://gehant.local:8080'
	});
	var process_payment = document.getElementById('process_payment');
	process_payment.addEventListener('click', function() {
		
		gifLoader("#process_payment", "Valider les achats")
		$("#process_payment").attr("disabled","disabled")
		
		CinetPay.setSignatureData({
			amount: parseInt(document.getElementById('amount').value),
			trans_id: Date.now(),
			currency: document.getElementById('currency').value,
			designation: Date.now()
		});
		CinetPay.getSignature();
	});

	CinetPay.on('error', function(e) {
		const message = 'Error code:' + e.code + 'Message::' + e.message;
		createErrorNotif(message,3,"top-right")
		setTimeout(function(){ $(location).attr("href","/panier") },3000)
	});
	
	CinetPay.on('paymentPending', function() {});
	
	CinetPay.on('signatureCreated', function() {})
	 
	CinetPay.on('paymentSuccessfull', function(paymentInfo) {
		if (typeof paymentInfo.lastTime != 'undefined') {

			if (paymentInfo.cpm_result == '00') {
				//paymentInfo.cpm_amount
				$.post("/buy-now",{prix: 100},function(data){
					if(data.type == "session"){
						$(location).attr("href","/")
					}else if(data.type == "success"){
						$(location).attr("href","/membre/historique-achats")
					}else{
						
							$("#process_payment").removeAttr("disabled")
						
						if(data.msg != null){
							createErrorNotif(data.msg,3,"top-right")
						}
			}
		},"json")
				
			} else {
				const message = 'Une erreur est survenue :' + paymentInfo.cpm_error_message+'.Veuillez réessayer ultérieurement.';
				createErrorNotif(message,5,"top-right")
				setTimeout(function(){ $(location).attr("href","/panier") },5000)
			}
		}
	});

	$(".btn-delete-formation-panier").click(function() {

		$.post("/delete-panier", { id: $(this).val() }, function(data) {

			if (data.type == "success") {
				location.reload();
			} else if (data.type == "session") {
				$(location).attr("href", "/compte/login")
			} else {
				createErrorNotif(data.id, 3, "top-right")
			}

		}, "json")


	})


})