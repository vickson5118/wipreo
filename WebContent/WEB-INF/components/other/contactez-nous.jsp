<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Contactez-nous | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/other/contactez-nous.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
		
			<div class="contactez-header"></div>
			
			<div class="container">
			
				<div class="nos-infos">
				
					<div class="row justify-content-md-center">
					
						<div class="col-md-4 one-info info-mail-content">
							<img src="/inc/images/icones/contact/email.png" alt="Email icone" />
							<h3>Email</h3>
							<div>info@wipreo.net</div>
						</div>
						
						<div class="col-md-4 one-info info-num-content">
							<img src="/inc/images/icones/contact/phone.png" alt="Téléphone icone" />
							<h3>Téléphone</h3>
							<div>+225 07 79 79 05 03</div>
							<div>+225 07 08 08 90 77</div>
						</div>
						
						<div class="col-md-4 one-info info-rs-content">
							<img src="/inc/images/icones/contact/network.png" alt="Réseaux sociaux icone" />
							<h3>Rejoingez-nous</h3>
							<a href=""><img src="/inc/images/icones/contact/facebook.png" alt="Facebook" class="rs-icones"/></a>
							<a href=""><img src="/inc/images/icones/contact/linkedin.png" alt="LinkedIn" class="rs-icones"/></a>
							<a href=""><img src="/inc/images/icones/contact/youtube.png" alt="Youtube" class="rs-icones"/></a>
						</div>
						
					</div>
					
				</div>
			</div>
			
			
			<div class="container form-contact-container">
				<div class="form-titre-container">
					<span>Contactez-nous</span>
				</div>
				<form>
					<div class="row">
						<div class="col-md-6">
							<div class="form-floating mb-3">
							  <input type="text" class="form-control" id="nom" placeholder="Nom" name="nom">
							  <label for="nom">Nom</label>
							  <div class="error"></div>
							</div>
						</div>
						
						<div class="col-md-6">
							<div class="form-floating mb-3">
							  <input type="text" class="form-control" id="prenoms" placeholder="Prenoms" name="prenoms">
							  <label for="prenoms">Prenoms</label>
							   <div class="error"></div>
							</div>
						</div>
						
						<div class="col-md-6">
							<div class="form-floating mb-3">
							  <input type="text" class="form-control" id="telephone" placeholder="Telephone" name="telephone">
							  <label for="telephone">Téléphone</label>
							   <div class="error"></div>
							</div>
						</div>
						
						<div class="col-md-6">
							<div class="form-floating mb-3">
							  <input type="email" class="form-control" id="contact-email" placeholder="Adresse email" name="contact-email">
							  <label for="contact-email">Adresse email</label>
							   <div class="error"></div>
							</div>
						</div>
						
						<div class="col-md-12">
							<div class="form-floating mb-3">
							  <input type="text" class="form-control" id="objet" placeholder="Objet" name="objet">
							  <label for="objet">Objet</label>
							   <div class="error"></div>
							</div>
						</div>
						
						<div class="col-md-12">
							<div class="form-floating">
							  <textarea class="form-control" placeholder="Message" id="message" style="height: 200px" name="message"></textarea>
							  <label for="message">Message</label>
							   <div class="error"></div>
							</div>
						</div>
						<button id="send-message" type="button">Envoyez le message</button>
					</div>
				</form>
			</div>
			
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/other/contactez-nous.js" type="text/javascript"></script>
	</body>
</html>