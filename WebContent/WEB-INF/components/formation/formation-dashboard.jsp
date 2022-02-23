<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Formation - <c:out value="${requestScope.formation.titre}" /> | Wipreo</title>
<meta name="description" content="${requestScope.formation.description}" />
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/formation/formation-dashboard.css" type="text/css" />
<link href="https://vjs.zencdn.net/7.17.0/video-js.css" rel="stylesheet" />
</head>
<body>

	<!-- Header -->
	<c:import url="/inc/other/header.jsp" />

	<div class="container-fluid ancre-container">
		<!-- menu formation -->
		<div class="container-fluid titre-container">
			<div class="container titre-content">
				<h1>
					Formation -
					<c:out value="${requestScope.formation.titre}" />
				</h1>
			</div>
		</div>



		<div class="container-fluid link-container">
			<div class="container link-content">
				<a href="#pourQui">Pour qui ?</a> 
				<a href="#objectifs">Objectifs</a>
				<a href="#programme">Programme</a> 
				<a href="#pointsCles">Points clés</a> 
				<a href="#avis">Avis</a>
			</div>
		</div>
	</div>



	<!-- Banniere de présentation -->
	<div class="container-fluid formation-dashboard-banniere">
		<div class="container">
			<h1>
				Formation - <c:out value="${requestScope.formation.titre}" />
			</h1>
			<h3>
				<c:out value="${requestScope.formation.but}" />
			</h3>
			<div class="banniere-note">
				<div class="rating" data-rate-value=<c:out value="${requestScope.formation.rating}"/>></div> 
				<span>(<c:out value="${requestScope.formationNombreCommentaire}" default="0"/> avis)</span>
			</div>
			<div class="banniere-formateur-infos">
				<span>Crée par <a href="/formateur/${requestScope.formation.auteur.profil}"><c:out value="${requestScope.formation.auteur.prenoms}" /> <c:out value="${requestScope.formation.auteur.nom}" /></a></span><br /> 
				<span>Derniere mise à jour : <c:out value="${requestScope.formation.dateCreation}" />
				</span><br /> <span class="banniere-formation-language">Français</span>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-8 formation-presentation-container">

					<div class="formation-description">${requestScope.formation.description}</div>

					<div class="row cible-container" id="pourQui">
						<h3>A qui s'adresse cette formation ?</h3>
						<div class="col-md-6 who-container">
							<p>
								<i class="bi bi-person"></i>Pour qui ?
							</p>
							<ul>
								<c:forTokens items="${requestScope.formation.pourQui}" delims=";" var="cible">
									<li><c:out value="${cible}"/></li>
								</c:forTokens>
							</ul>
							
						</div>
						<div class="col-md-6 prerequis-container">
							<p>
								<i class="bi bi-list-check"></i> Prérequis
							</p>
							<ul>
								<c:forTokens items="${requestScope.formation.prerequis}" delims=";" var="prerequis">
									<li><c:out value="${prerequis}"/></li>
								</c:forTokens>
							</ul>
						</div>
					</div>


					<div class="row objectif-formation-container" id="objectifs">
						<h3>Les objectifs de la formation</h3>
						<ul class="col-md-12 objectif-content">
							<c:forTokens items="${requestScope.formation.objectifs}" delims=";" var="objectif">
								<li><c:out value="${objectif}"/></li>
							</c:forTokens>
						</ul>
					</div>


					<div class=" row programme-formation-container" id="programme">
						<h3>Le programme de la formation</h3>

						<div class="accordion accordion-flush" id="accordionFlushExample">

							<c:forEach items="${requestScope.listeModule}" var="module" varStatus="status">
							
								<div class="accordion-item">
								<h2 class="accordion-header" id="panelsStayOpen-heading${status.count}">
									<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapse${status.count}" aria-expanded="true" aria-controls="panelsStayOpen-collapse${status.count}">${status.count} - <c:out value="${module.titre}"/> </button>
								</h2>
								<div id="panelsStayOpen-collapse${status.count}" class="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-heading${status.count}">
									<div class="accordion-body">
									
										<div class="description-container">
											<c:out value="${module.description}"/>
										</div>
										
										<div class="lesson-item">
											<ul>
												<c:forEach items="${requestScope.listeLesson}" var="lesson">
													<c:if test="${module.id == lesson.module.id}">
														<li><c:out value="${lesson.titre}"/> </li>
													</c:if>
												</c:forEach>
												
												
											</ul>
										</div>

										<div class="exercice-item">
											<ul>
												<c:forEach items="${requestScope.listeExercice}" var="exercice">
													<c:if test="${module.id == exercice.module.id}">
														<li>Quiz - <c:out value="${exercice.titre}"/> </li>
													</c:if>
												</c:forEach>
												
											</ul>
										</div>

									</div>
								</div>
							</div>
								
								
							</c:forEach>


						</div>

					</div>


					<div class="points-forts-container" id="pointsCles">
						<h3>Les points clés de la formation</h3>
						<ul class="points-forts-content">
							<c:forTokens items="${requestScope.formation.pointsCles}" delims=";" var="points">
								<li><c:out value="${points}"/></li>
							</c:forTokens>
						</ul>
					</div>

					<div class="avis-container" id="avis">
						<h3>Les avis sur la formation</h3>
						<div class="row">
						
							<div class="col-md-4 num-notation-container">
								<div class="notation-num-content"><c:out value="${requestScope.formation.rating}"/></div>
								<div class="notation-star-content">
									<div class="rating" data-rate-value=<c:out value="${requestScope.formation.rating}"/>></div> 
								</div>
								<div>Note de la formation</div>
							</div>
							
							<div class="col-md-8 notation-progress-container">
							
								<div class="row one-notation-progress">
								
									<div class="col-md-8">
										<div class="progress">
											<div class="progress-bar" role="progressbar" style="width: <c:out value="${requestScope.notationCinqPercent}" default="100" />%"></div>
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="rating progress-rating" data-rate-value=5></div> 
										<span class="progress-percent"><c:out value="${requestScope.notationCinqPercent}" default="100"/>%</span>
									</div>
								</div>

								<div class="row one-notation-progress">
									<div class="col-md-8">
										<div class="progress">
											<div class="progress-bar" role="progressbar" style="width: <c:out value="${requestScope.notationQuatrePercent}" default="0"/>%"></div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="rating progress-rating" data-rate-value=4></div> 
										<span class="progress-percent"><c:out value="${requestScope.notationQuatrePercent}" default="0"/>%</span>
									</div>
								</div>

								<div class="row one-notation-progress">
									<div class="col-md-8">
										<div class="progress">
											<div class="progress-bar" role="progressbar" style="width: <c:out value="${requestScope.notationTroisPercent}" default="0"/>%"></div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="rating progress-rating" data-rate-value=3></div> 
										<span class="progress-percent"><c:out value="${requestScope.notationTroisPercent}" default="0"/>%</span>
									</div>
								</div>

								<div class="row one-notation-progress">
									<div class="col-md-8">
										<div class="progress">
											<div class="progress-bar" role="progressbar" style="width: <c:out value="${requestScope.notationDeuxPercent}" default="0"/>%"></div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="rating progress-rating" data-rate-value=2></div> 
										<span class="progress-percent"><c:out value="${requestScope.notationDeuxPercent}" default="0"/>%</span>
									</div>
								</div>

								<div class="row one-notation-progress">
									<div class="col-md-8">
										<div class="progress">
											<div class="progress-bar" role="progressbar" style="width: <c:out value="${requestScope.notationUnPercent}" default="0"/>%"></div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="rating progress-rating" data-rate-value=2></div> 
										<span class="progress-percent"><c:out value="${requestScope.notationUnPercent}" default="0"/>%</span>
									</div>
								</div>
							</div>
						</div>
							
							<c:if test="${!empty requestScope.listeCommentaire}">
							
								<div class="comments-container">
									
									<c:forEach items="${requestScope.listeCommentaire}" var="commentaire">
										<div class="comment">
											<p class="comment-prop-nom"><c:out value="${commentaire.utilisateur.prenoms}"/> <c:out value="${commentaire.utilisateur.nom}"/></p>
											<p class="comment-content"><c:out value="${commentaire.avis}"/></p>
										</div>
									</c:forEach>
								
								</div><!-- Fin comments-container -->
							
							</c:if>
					</div>
				</div>
				
				<aside class="col-md-4 formation-aside" <c:if test="${requestScope.isPaid}">style="height: 580px;"</c:if>>
					<!--  <div class="aside-header">
						<button>Télécharger le PDF</button>
					</div>-->
					<div class="aside-body">
						<div class="video-formation-presentation">
								<iframe style="width: 100%;height: 211px;"
										src="https://player.vimeo.com/video/${requestScope.formation.videoPresentation}" frameborder="0" name="player" webkitallowfullscreen
										mozallowfullscreen allowfullscreen></iframe>
							
						</div>

						<div class="info-line-container">

							<div class="one-info-line time-content">
								<span><i class="bi bi-clock"></i> Durée</span> <span><c:out
										value="${requestScope.formation.nombreHeures}" /> heures</span>
							</div>

							<c:if test="${!requestScope.isPaid}">
								<div class="one-info-line money-content">
									<span><i class="bi bi-currency-dollar"></i> Prix</span> <span><c:out
											value="${requestScope.formation.prix}" /> FCFA</span>
								</div>
							</c:if>

							<div class="one-info-line btn-container">
								
								<c:choose>
									<c:when test="${requestScope.isPaid}">
										<a href="/apprendre/formations/${requestScope.formation.domaine.titreUrl}/${requestScope.formation.titreUrl}" title="Accéder au cours" class="go-to-formation" style="text-decoration: none;">Accéder au cours</a>
									</c:when>
									<c:otherwise>
									
										<c:choose>
											<c:when test="${requestScope.isPanier}">
												<button title="Accéder au panier" class="go-to-basket-home" type="button">Accéder au panier</button>
											</c:when>
											<c:otherwise>
												<button title="Ajouter au panier" class="add-to-basket" value="${requestScope.formation.id}" type="button">Ajouter au panier</button>
											</c:otherwise>
										</c:choose>
										
										<c:forEach items="${sessionScope.listeFavoris}" var="favoris">
											
											<c:if test="${favoris.formation.id == requestScope.formation.id}">
												<c:set var="isFavoris" value="true" scope="page"/>
												<button title="Ajouter à mes favoris" class="remove-favoris"
													value="${favoris.id}">
													<i class="bi bi-heart-fill"></i>
												</button>
											</c:if>
										</c:forEach>
										
										<c:if test="${!isFavoris}">
											<button title="Ajouter à mes favoris" class="add-favoris"
													value="${requestScope.formation.id}-${sessionScope.utilisateur.id}">
													<i class="bi bi-heart"></i>
												</button>
										</c:if>
										
										<!-- 
										<button title="Acheter dès maintenant">Acheter dès
											maintenant</button> -->
									</c:otherwise>
								</c:choose>
								
								
							</div>

							<div class="one-info-line banniere-contact">
								<a href="/contactez-nous">Contactez-nous</a>
							</div>

							<div class="one-info-line banniere-rs-container">
								<span>Partager la formation</span>
								<div class="banniere-rs">
									<a href=""><i class="bi bi-facebook"></i></a> <a href=""><i
										class="bi bi-twitter"></i></a> <a href=""><i
										class="bi bi-linkedin"></i></a> <a href=""><i
										class="bi bi-link"></i></a>
								</div>
							</div>
						</div>

					</div>
				</aside>

			</div>
		</div>
	</div>
	
	<c:if test="${!empty requestScope.listeFormation}">
		<div class="container-fluid formations-recommandes-container">
		<div class="container">
			<h2>Ces formations peuvent aussi vous intéresser</h2>

			<c:forEach items="${requestScope.listeFormation}" var="formation">
			
				<div class="one-formation-recommande">
					<h1><a style="text-decoration: none;" href="/formations/<c:out value="${formation.domaine.titreUrl}"/>/<c:out value="${formation.titreUrl}"/>"><c:out value="${formation.titre}"/></a></h1>
					<h3><c:out value="${formation.but}"/></h3>
					<div class="rating" data-rate-value=<c:out value="${formation.rating}"/>></div>
					<div class="price-day-panier-container">
						<div><c:out value="${formation.prix}"/> FCFA</div>
					</div>
				</div>
			</c:forEach>

			<div style="text-align: center;">
				<a href="/formations/${requestScope.formation.domaine.titreUrl}" class="plus-categorie-formations">Autres formations en <c:out value="${requestScope.domaineTitre}"/> </a>
			</div>

		</div>
		
		</div>
	</c:if>
	

	<!-- Footer -->
	<c:import url="/inc/other/footer.jsp" />

	<c:import url="/inc/other/js.jsp" />
	<script src="/inc/js/formation/formation-dashboard.js" type="text/javascript"></script>
	<script src="/inc/js/rater.min.js" type="text/javascript"></script>
</body>
</html>