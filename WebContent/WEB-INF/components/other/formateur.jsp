<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<title>Formateur <c:out value="${requestScope.utilisateur.prenoms}"/> <c:out value="${requestScope.utilisateur.nom}"/> | Wipreo</title>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/other/formateur.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			<div class="container">
			
			
				<div class="row" id="formateur-infos-perso-container">
					<div class="col-md-10 offset-md-1">
						
						<div class="row">
						
						<div class="col-md-8">
								<div class="page-designation">Formateur</div>
								<h1><c:out value="${requestScope.utilisateur.prenoms}"/> <c:out value="${requestScope.utilisateur.nom}"/></h1>
								<div class="formateur-fonction"><c:out value="${requestScope.utilisateur.fonction}"/></div>
								
								<div class="infos-perso-extras">
										<div class="formation-number">
											<div>Nombre total de formation</div>
											<div><c:out value="${requestScope.utilisateur.nombreFormation}"/></div>
										</div>
										
										<div class="formateur-rating">
											<div>Note du formateur</div>
											<div><c:out value="${requestScope.utilisateur.rating}"/> / 5</div>
										</div>
								</div>
								
							</div>
							<div class="col-md-4" id="profil-picture">
								<c:choose>
									<c:when test="${requestScope.utilisateur.photo != null}">
										<img src="${requestScope.utilisateur.photo}" alt="Photo de profil du formateur" />
									</c:when>
									<c:otherwise>
										<div class="default-profil-picture"> <c:out value="${requestScope.utilisateur.initial}"/> </div>
									</c:otherwise>
								</c:choose>
								
								<div class="formateur-rs">
									<a href='<c:url value="${requestScope.utilisateur.site}" />'><img src="/inc/images/icones/fprofil/web.png" alt="Icône site personnel" /></a>
									<a href="<c:url value="${requestScope.utilisateur.linkedin}" />"><img src="/inc/images/icones/fprofil/linkedin.png" alt="icône LinkedIn" /></a>
									<a href="<c:url value="${requestScope.utilisateur.youtube}" />"><img src="/inc/images/icones/fprofil/youtube.png" alt="icône Youtube" /></a>
									<a href="<c:url value="${requestScope.utilisateur.facebook}" />"><img src="/inc/images/icones/fprofil/facebook.png" alt="icône Facebook" /></a>
									<a href="<c:url value="${requestScope.utilisateur.twitter}" />"><img src="/inc/images/icones/fprofil/twitter.png" alt="Icône twitter" /></a>
								</div>
							</div>

					</div><!-- fin row -->
					
					<div class="row bio-container" style="margin-bottom: 40px;">
						<h5>Biographie</h5>
						<p> <c:out value="${requestScope.utilisateur.biographie}"></c:out> </p>
					</div><!-- fin row -->
					
					<div class="row">
						
						<div class="col-md-11">
							
							<div class="row">
								
								<c:forEach items="${requestScope.listeFormation}" var="formation">
									<div class="col-md-4 formation-item">
		
										<div class="card">
											<img src='<c:out value="${formation.illustration}"/>' class="card-img-top" alt='<c:out value="${formation.titre}"/>'>
											<div class="card-body">
												<h1 class="card-title">
													<a href="/formations/<c:out value="${formation.domaine.titreUrl}" />/<c:out value="${formation.titreUrl}" />"><span class="cours-title"><c:out value="${formation.titre}" /></span></a>
												</h1>
												<div class="auteur">
													<span><c:out value="${formation.auteur.prenoms}" /> <c:out value="${formation.auteur.nom}" /></span>
												</div>
												<div class="formation-extra-container">
													<span class="fmark">Note : </span>
													<div class="rating" data-rate-value=<c:out value="${formation.rating}"/>></div>
		
													<span class="views">Vues: 70.586</span>
												</div>
												<div class="formation-prix">
													<b><c:out value="${formation.prix}" /> FCFA</b>
												</div>
												<!--  <div class="formation-promo"><b><c:out value="${formation.prix}"/> FCFA</b> </div>-->
											</div>
										</div>
									</div>
								</c:forEach>
								
							</div>
							
						</div>
						
					</div>
						
					</div>
				</div>
				
				
			</div>
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/rater.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var rateOptions = {
			readonly : true
		}
		$(".rating").rate(rateOptions);
	</script>
	</body>
</html>