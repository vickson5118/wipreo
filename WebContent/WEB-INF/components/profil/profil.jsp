<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Mon profil | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		 <link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
		 <link rel="stylesheet" href="/inc/css/profil/menu-profil.css" type="text/css"/>
		 <link rel="stylesheet" href="/inc/css/profil/profil.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			<div class="container">
			
				<c:import url="menu.jsp"/>
				
				
				
				<div class="row">
				
					<div class="profil-picture">
							
							<p>Photo de profil</p>
							
							<c:choose>
								<c:when test="${sessionScope.utilisateur.photo == null}">
										
										<form method="post" enctype="multipart/form-data" id="profil-picture-form">
											<input type="file" id="profilFile" name="profilFile"/>
											<label for="profilFile" class="profllLabel"><c:out value="${sessionScope.utilisateur.initial}"/></label>
											<input type="submit" id="submit-profil-picture" />
										</form>
								</c:when>
								
								<c:otherwise>
									
									<form method="post" enctype="multipart/form-data" id="profil-picture-form">
											<input type="file" id="profilFile" name="profilFile"/>
											<label for="profilFile"><img src="${sessionScope.utilisateur.photo}" alt="Photo de profil" /></label>
											<input type="submit" id="submit-profil-picture" />
										</form>
								</c:otherwise>
								
								
							</c:choose>
						
							
						</div>
					
					<form class="col-md-12">
					
					<p> A propos de moi</p>
				
					<div class="row">
					
						<div class="form-floating mb-3 col-md-4">
						  <input type="text" class="form-control" id="nom" placeholder="Nom" name="nom" value='<c:out value="${sessionScope.utilisateur.nom}"/>'>
						  <label for="nom">Nom</label>
						  <div class="error"></div>
						</div>
						
						<div class="form-floating mb-3 col-md-4">
						  <input type="text" class="form-control" id="prenoms" placeholder="Prenoms" name="prenoms" value='<c:out value="${sessionScope.utilisateur.prenoms}"/>'>
						  <label for="prenoms">Prenoms</label>
						  <div class="error"></div>
						</div>
						
						<div class="form-floating col-md-4">
						  <input type="tel" class="form-control" id="telephone" placeholder="Numéro téléphonique" name="telephone" value='<c:out value="${sessionScope.utilisateur.telephone}"/>'>
						  <label for="telephone">Numero téléphonique</label>
						  <div class="error"></div>
						</div>
						
					</div>
					
					
					
					<div class="row">
						
						<div class="form-floating col-md-4 mb-3">
						  <input type="text" class="form-control" id="date-naissance" placeholder="Date de naissance" name="date-naissance" value='<c:out value="${sessionScope.utilisateur.dateNaissance}"/>'>
						  <label for="date-naissance">Date de naissance</label>
						  <div class="error"></div>
						</div>
						
						<div class="col-md-4 mb-3">
								<select class="form-select" aria-label="Sexe" id="sexe" name="sexe" style="height: 58px;">
									
									<c:forEach items="${requestScope.listeSexe}" var="sexe">
										<c:choose>
											<c:when test="${sexe.id == sessionScope.utilisateur.sexe.id}">
												<option value="${sexe.id}" selected="selected"><c:out value="${sexe.nom}" /></option>
											</c:when>
											<c:otherwise>
												<option value="${sexe.id}"><c:out value="${sexe.nom}" /></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<div class="error"></div>
						</div>
						
						<div class="col-md-4">
								<select class="form-select" aria-label="Pays" id="pays" name="pays" style="height: 58px">
								  <c:forEach items="${requestScope.listePays}" var="pays">
										<c:choose>
											<c:when test="${pays.id == sessionScope.utilisateur.pays.id}">
												<option value="${pays.id}" selected="selected"><c:out value="${pays.nom}" /></option>
											</c:when>
											<c:otherwise>
												<option value="${pays.id}"><c:out value="${pays.nom}" /></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
							</select>
							<div class="error"></div>
						</div>
						
					</div>
					
					<button type="button" id="update-user-infos">Valider la modification</button>
					</form>
					
				</div>
				
			</div>
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
		<script src="/inc/js/profil/profil.js" type="text/javascript"></script>
	</body>
</html>