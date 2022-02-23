<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Mes informations de sécurité | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/profil/menu-profil.css" type="text/css"/>
		<link rel="stylesheet" href="/inc/css/profil/profil-securite.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			<div class="container">
				<c:import url="menu.jsp"/>
				
				<div class="row justify-content-center">
					<div class="col-md-7">
						<form>
						
							<p>Modification de l'adresse email</p>
						
						<div class="form-floating mb-3">
						  <input type="email" class="form-control" id="email" placeholder="Adresse email" name="email" value='<c:out value="${sessionScope.utilisateur.email}" />'>
						  <label for="floatingInput">Adresse email</label>
						  <div class="error"></div>
						</div>
						
						<div class="form-floating mb-3">
						  <input type="email" class="form-control" id="new-email" placeholder="Nouvelle adresse email" name="new-email">
						  <label for="new-email">Nouvelle adresse email</label>
						  <div class="error"></div>
						</div>
						
						
						<button type="button" id="btn-update-email">Valider la nouvelle adresse mail</button>
						
						</form>
					</div>
				</div>
				
				
				<div class="row justify-content-center" style="margin-top: 50px;">
					<div class="col-md-7">
						<form>
						
							<p>Modification du mot de passe</p>
						
							<div class="form-floating mb-3">
							  <input type="password" class="form-control" id="new-password" placeholder="Nouveau mot de passe" name="new-password">
							  <label for="new-password">Nouveau mot de passe</label>
							  <div class="error"></div>
							</div>
							
							<div class="form-floating mb-3">
							  <input type="password" class="form-control" id="repeat-new-password" placeholder="Répéter le nouveau mot de passe" name="repeat-new-password">
							  <label for="repeat-new-password">Répéter le nouveau mot de passe</label>
							  <div class="error"></div>
							</div>
							
							<button type="button" id="btn-update-password">Rédéfinir le mot de passe</button>
						
						</form>
					</div>
				</div>
				
			</div>
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/profil/profil-securite.js" type="text/javascript"></script>
	</body>
</html>