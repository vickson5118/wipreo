<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Mon profil instructeur | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		 <link rel="stylesheet" href="/inc/css/profil/menu-profil.css" type="text/css"/>
		 <link rel="stylesheet" href="/inc/css/profil/profil-instructeur.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			<div class="container">
			
			<c:import url="menu.jsp"/>
			
			<form>
				<p>A propos de l'instrcuteur</p>
				<div class="row">
					
					<div class="form-floating mb-3 col-md-6">
					  <input type="text" class="form-control" id="fonction" placeholder="Fonction" name="fonction" value='<c:out value="${sessionScope.utilisateur.fonction}"/>'>
					  <label for="fonction" style="margin-left: 15px;">Fonction</label>
					  <div class="error"></div>
					</div>
					
					<div class="form-floating mb-3 col-md-6">
					  <input type="text" class="form-control" id="site" placeholder="Site web" name="site" value='<c:out value="${sessionScope.utilisateur.site}"/>'>
					  <label for="site" style="margin-left: 15px;">Site web</label>
					  <div class="error"></div>
					</div>
					
				</div>
				
				<div class="row">
					
					<div class="col-md-6">
						<label for="facebook" class="form-label">Facebook</label>
						<div class="input-group mb-3">
						  <span class="input-group-text">https://www.facebook.com/</span>
						  <input type="text" class="form-control" id="facebook" aria-describedby="facebook" style="height: 58px;" value='<c:out value="${sessionScope.utilisateur.facebook}"/>'>
							<div class="error"></div>
						</div>					
					</div>
					
					
					<div class="col-md-6">
						<label for="twitter" class="form-label">Twitter</label>
						<div class="input-group mb-3">
						  <span class="input-group-text"> https://www.twitter.com/ </span>
						  <input type="text" class="form-control" id="twitter" aria-describedby="twitter" style="height: 58px;right: 0;" value='<c:out value="${sessionScope.utilisateur.twitter}"/>'>
						<div class="error" style="min-width: 100%;"></div>
						</div>					
					</div>
					
					
				</div>
				
				<div class="row">
				
					<div class="col-md-6">
						<label for="linkedin" class="form-label">Linkedin</label>
						<div class="input-group mb-3">
						  <span class="input-group-text">https://www.linkedin.com/</span>
						  <input type="text" class="form-control" id="linkedin" aria-describedby="linkedin" style="height: 58px;" value='<c:out value="${sessionScope.utilisateur.linkedin}"/>'>
							<div class="error"></div>
						</div>					
					</div>
					
					<div class="col-md-6">
						<label for="youtube" class="form-label">Youtube</label>
						<div class="input-group mb-3">
						  <span class="input-group-text">https://www.youtube.com/</span>
						  <input type="text" class="form-control" id="youtube" aria-describedby="youtube" style="height: 58px;" value='<c:out value="${sessionScope.utilisateur.youtube}"/>'>
							<div class="error"></div>
						</div>					
					</div>
				
				</div>
				
				<div class="row justify-content-center">
					
					<div class="mb-3">
					  <label for="biographie" class="form-label">Biographie</label>
					  <textarea class="form-control" id="biographie" rows="5" name="biographie"><c:out value="${sessionScope.utilisateur.biographie}"/></textarea>
					  <div class="error"></div>
					</div>
					
					
				</div>
				
				<button type="button" id="update-instructeur-infos">Valider les informations</button>
			</form>
				
			</div>
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/profil/profil-instructeur.js" type="text/javascript"></script>
	</body>
</html>