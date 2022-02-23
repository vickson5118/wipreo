<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Apprenez avec la formation <c:out
		value="${sessionScope.formation.titre}" /> | Wipreo
</title>
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/formation/lire-formation.css"
	type="text/css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css"
	type="text/css">
<body>



	<div class="container-fluid">
		<div class="lecteur-header">
			<a class="logo-container" href="/"><img
				src="/inc/images/logo.png" alt="logo Wipreo"> </a>
			<h1>
				<c:out value="${sessionScope.formation.titre}" />
			</h1>
			<button type="button" class="btn-add-commentaire" title="Ajouter un commentaire" data-bs-toggle="modal" 
				data-bs-target="#staticBackdropAddCommentaire">
					<i class="bi bi-chat-left-quote-fill"></i>
				</button>

			<c:choose>
				<c:when test="${!requestScope.achat.formationTerminated}">
					<button type="button" title="Formation terminée" class="btn-terminate-formation" data-bs-toggle="modal" data-bs-target="#staticBackdropAddCertificat">
						<i class="bi bi-check"></i>
					</button>
				</c:when>
				<c:when test="${requestScope.achat.formationTerminated && requestScope.achat.certificat != null}">
					<a href="${requestScope.achat.certificat}" class="btn-download-certificat" title="Télécharger le certificat">
						<i class="bi bi-arrow-down-circle"></i>
					</a>
				</c:when>
			</c:choose>

		</div>

		<iframe style="width: 62%;"
			src="https://player.vimeo.com/video/672586862" width="1075"
			height="550" frameborder="0" name="player" webkitallowfullscreen
			mozallowfullscreen allowfullscreen></iframe>

		<div class="contenu-cours">
			<p>Contenu du cours</p>




			<div class="accordion accordion-flush" id="accordionFlushExample">

				<c:forEach items="${requestScope.listeModule}" var="module"
					varStatus="status">

					<div class="accordion-item">
						<h2 class="accordion-header"
							id="panelsStayOpen-heading${status.count}">
							<button class="accordion-button" type="button"
								data-bs-toggle="collapse"
								data-bs-target="#panelsStayOpen-collapse${status.count}"
								aria-expanded="true"
								aria-controls="panelsStayOpen-collapse${status.count}">
								<c:out value="${module.titre}" />
							</button>
						</h2>
						<div id="panelsStayOpen-collapse${status.count}"
							class="accordion-collapse collapse"
							aria-labelledby="panelsStayOpen-heading${status.count}">
							<div class="accordion-body">

								<div class="lesson-item">
									<c:forEach items="${listeLesson}" var="lesson">
										<c:if test="${module.id == lesson.module.id}">

											<a href="https://player.vimeo.com/video/${lesson.source}"
												target="player"
												class="
												<c:choose>
													<c:when test="${empty requestScope.listeLessonViewId}">lesson-source </c:when>
													<c:otherwise>
														<c:forEach items="${requestScope.listeLessonViewId}" var="lessonViewId">
															<c:choose>
																<c:when test="${lessonViewId == lesson.id}">lesson-view </c:when>
																<c:otherwise>lesson-source</c:otherwise>
															</c:choose>
															
														</c:forEach>
													</c:otherwise>
													</c:choose>"><c:out
													value="${lesson.titre}" /></a>
											<br />


										</c:if>
									</c:forEach>
								</div>

								<div class="exercice-item">
									<c:forEach items="${requestScope.listeExercice}" var="exercice">
										<c:if
											test="${module.id == exercice.module.id && exercice.finish}">
											<a
												href="/apprendre/exercices/${sessionScope.formation.domaine.titreUrl}/${sessionScope.formation.titreUrl}/${exercice.id}"
												class="<c:forEach items="${requestScope.listeExercicePassed}" var="exercicePassed"><c:choose><c:when test="${exercicePassed.exercice.id == exercice.id && exercicePassed.valide}">text-success</c:when><c:when test="${exercicePassed.exercice.id == exercice.id && !exercicePassed.valide}">text-error</c:when></c:choose></c:forEach>">
												<c:out value="${exercice.titre}" />
											</a>
										</c:if>
									</c:forEach>
								</div>

							</div>
						</div>
					</div>

				</c:forEach>

			</div>

		</div>


		<div class="container-fluid a-propos-cours-container">
			<p>A propos de la formation</p>
			
			<div class="formation-but">
				<c:out value="${sessionScope.formation.but}"></c:out>
			</div>

			<div class="row formation-description-container">
				<div class="col-md-3">Description</div>
				<div class="col-md-9"><c:out value="${sessionScope.formation.description}" /></div>
			</div>
			
			<div class="row formation-certificat-container">
				<div class="col-md-3">Certificat Wipreo</div>	
				<div class="col-md-9">
					Si vous terminez le cours entier vous obtenez un certificat Wipreo <br />
					<c:choose>
						<c:when test="${!requestScope.achat.formationTerminated}">
							<button type="button" title="Formation terminée" class="btn-terminate-formation" data-bs-toggle="modal" data-bs-target="#staticBackdropAddCertificat">
								Formation terminée
							</button>
						</c:when>
						<c:when test="${requestScope.achat.formationTerminated && requestScope.achat.certificat != null}">
							<a href="${requestScope.achat.certificat}" class="btn-download-certificat" title="Télécharger le certificat">
								Télécharger le certificat
							</a>
						</c:when>
					</c:choose>
					
				</div>
			</div>
			
			<div class="row formation-formateur-container">
				<div class="col-md-3">Formateur</div>
				<div class="col-md-9">
				
					<div class="image-profil">
						
						<c:choose>
							<c:when test="${sessionScope.formation.auteur.photo == null}">
								<c:out value="${sessionScope.formation.auteur.initial}"/>
							</c:when>
							<c:otherwise>
								<img src="${sessionScope.formation.auteur.photo}" alt="Photo de profil" />
							</c:otherwise>
						</c:choose>
						
					</div>
					
					<div class="formation-name">
						<c:out value="${sessionScope.formation.auteur.prenoms}" /> 
						<c:out value="${sessionScope.formation.auteur.nom}" />
					</div>
					
					<div class="fonction">
						<c:out value="${sessionScope.formation.auteur.fonction}" /> 
					</div>
					
					<div class="name-rs">
						<a href="${sessionScope.formation.auteur.site}"><img src="/inc/images/icones/fprofil/web.png" alt="Site Web" /></a>
						<a href="${sessionScope.formation.auteur.facebook}"><img src="/inc/images/icones/fprofil/facebook.png" alt="Facebook" /></a>
						<a href="${sessionScope.formation.auteur.twitter}"><img src="/inc/images/icones/fprofil/twitter.png" alt="Twitter" /></a>
						<a href="${sessionScope.formation.auteur.linkedin}"><img src="/inc/images/icones/fprofil/linkedin.png" alt="LinkedIn" /></a>
						<a href="${sessionScope.formation.auteur.youtube}"><img src="/inc/images/icones/fprofil/youtube.png" alt="Youtube" /></a>
					</div>
					
					
					
					<div class="biographie">
						<c:out value="${sessionScope.formation.auteur.biographie}" /> 
					</div>
					
					
					
				</div>
			</div>
			
			<div class="row commentaire-container">
			
					<div class="col-md-3">Commentaires</div>
					<div class="col-md-9">
						<c:forEach items="${requestScope.listeCommentaire}" var="commentaire">
						<div class="one-commentaire">
							<p class="comment-prop-nom">
								<c:out value="${commentaire.utilisateur.prenoms}"/> 
								<c:out value="${commentaire.utilisateur.nom}"/>
							</p>
							<p class="comment-content"><c:out value="${commentaire.avis}"/></p>
						</div>
					</c:forEach> 
					</div>
					
				</div>
			</div>

	<c:import url="modal/modal-add-commentaire.jsp" />
		<c:import url="modal/modal-add-certificat.jsp" />
		
		</div>
	
	<c:import url="/inc/other/js.jsp" />

	<script src="/inc/js/rater.min.js" type="text/javascript"></script>
	<script src="/inc/js/formation/lire-formation.js" type="text/javascript"></script>
	<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js" type="text/javascript"></script>
</body>
</html>