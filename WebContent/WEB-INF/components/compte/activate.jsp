<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Rédefinissez votre mot de passe | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/compte/activate.css" type="text/css" />
	</head>
	<body>
		
		<div class="container-fluid">
		
			<a class="logo-container" href="/"> 
					<img src="/inc/images/logo.png" alt="logo Wipreo" width="160" height="50">
				</a>
				
			<div class="container">
		
				<div class="redefinir-password-container">
					
					<p>Redefinissez votre mot de passe</p>
					
					<form method="post" id="redefinir-password-form">
						
						<div class="form-floating">
						  <input type="password" class="form-control" id="password" placeholder="Mot de passe" name="password">
						  <label for="password">Mot de passe</label>
						  <div class="error"></div>
						</div>
	
							<div class="pass-security-container">
								<div class="pass-security"></div>
								<div class="pass-security"></div>
								<div class="pass-security"></div>
								<div class="pass-security"></div>
								<div class="pass-security"></div>
							</div>
						
						<div class="form-floating" style="margin-top: 20px;">
						  <input type="password" class="form-control" id="repeat-password" placeholder="Repéter le mot de passe" name="repeat-password">
						  <label for="repeat-password">Repéter le mot de passe</label>
						  <div class="error"></div>
						</div>
						
						<button type="submit" id="btn-valide-change-password">Valider le mot de passe</button>
						<a href="/">Poursuivre <i class="bi bi-arrow-right"></i></a>
						
					</form>
				</div>
				
			</div>
		</div>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/compte/activate.js" type="text/javascript"></script>
	</body>
</html>