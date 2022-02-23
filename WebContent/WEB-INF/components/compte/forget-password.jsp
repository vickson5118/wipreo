<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Mot de passe oublié, redefinisez le | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/compte/forget-password.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			<div class="container">
				<div class="row">
					<div class="col-md-6 forget-container">
						<h1>Vous avez oublié votre mot de passe ?<br />
						Pas de panique, redefinissez-le et profitez à nouveau de notre contenu.</h1>
						
						<p>Assurez vous de rentrer une adresse email valide car vous recevrez la marche à suivre à cette adresse</p>
					
						<div class="form-floating mb-3 inscription-info">
							<input type="email" class="form-control" id="reset-pass-email"placeholder="Adresse email" name="reset-pass-email"> 
							<label for="reset-pass-email">Adresse email</label>
							<div class="error"></div>
						</div>
						<button id="btn-reset-pass-valide" class="mb-4" type="button">Reinitialiser le mot de passe</button>
					</div>
					<div class="col-md-6 img-forget-password-container"><i class="bi bi-unlock-fill"></i></div>
				</div>
			</div>
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/compte/forget-password.js" type="text/javascript"></script>
	</body>
</html>