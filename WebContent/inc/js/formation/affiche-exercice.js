$(document).ready(function(){
	
	/*$(".btn-send-correction").attr("disabled","disabled");
	
	$("#finish").on("click",function(){
		console.log($(this).val())
		
	})*/
	
	$(".reponse-check").click(function(){
		var questionId = $(this).parent().parent().parent().find(".question-id").text();
		var reponseId = $(this).attr("id")
		
		$.post("/reponse-check",{questionId,reponseId},function(data){
			if(data.msg != null){
				createErrorNotif(data.msg,3,"top-left")
			}
			
		},"json")
		
	})
	
	$("#form-exercice").submit(function(event){
		event.preventDefault();
		
		gifLoader(".btn-send-correction", "Soumettre pour correction")
		$(".btn-send-correction").attr("disabled","disabled")

		$.post("/exercice/correction",function(data){
			if(data.msgFinal != null){
				createInfoNotif(data.msgFinal,3,"top-right")
				setTimeout(function(){location.reload()},3000)
			}
		},"json")
	})
	
})