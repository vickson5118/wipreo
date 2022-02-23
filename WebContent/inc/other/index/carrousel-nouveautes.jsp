<c:if test="${!empty requestScope.listeHuitDerniersFormation}">
	<div class="top-nouveautes">
	
		<p>Top nouveautés</p>
	
		<div class="row slider-nouveautes">
			
			<c:forEach items="${requestScope.listeHuitDerniersFormation}" var="formation">
			
					<div class="col-md-12 col-sm-12">
					<div class="card">
						<img src='<c:out value="${formation.illustration}"/>' class="card-img-top" alt="<c:out value="${formation.titre}"/>" height="200px" />
						<div class="card-body">
						
							<c:choose>
								<c:when test="${empty sessionScope.listeFavoris}">
									<button title="Ajouter à mes favoris" class="all-favoris-add add-favoris" value="${formation.id}">
											<i class="bi bi-heart-fill"></i>
											</button>
								</c:when>
								<c:otherwise>
									<c:set var="isFavoris" value="false" />	
									<c:forEach items="${sessionScope.listeFavoris}" var="favoris">
										<c:if test="${favoris.formation.id == formation.id}">
											<c:set var="isFavoris" value="true" />
											<c:set var="favorisId" value="${favoris.id}" />
										</c:if>
									</c:forEach>
									
									<c:choose>
										<c:when test="${isFavoris}">
											
											<button title="Supprimer à mes favoris" class="all-favoris-remove remove-favoris" value="${favorisId}">
												<i class="bi bi-heart-fill"></i>
											</button>
											
										</c:when>	
										<c:otherwise>
										
											<button title="Ajouter à mes favoris" class="all-favoris-add add-favoris" value="${formation.id}">
												<i class="bi bi-heart-fill"></i>
											</button>
											
										</c:otherwise>
									</c:choose>
								
								</c:otherwise>
							</c:choose>
							
							<h1><a href="/formations/<c:out value="${formation.domaine.titreUrl}"/>/<c:out value="${formation.titreUrl}"/>" class="card-title"><c:out value="${formation.titre}"/></a></h1>
						</div>
						<div class="card-footer">
							<span class="coach-name"><c:out value="${formation.auteur.prenoms}"/> <c:out value="${formation.auteur.nom}"/></span><br />
							<span class="rating-number"><c:out value="${formation.rating}"/></span><div class="rating" data-rate-value=<c:out value="${formation.rating}"/>></div>
							<span class="prix"><c:out value="${formation.prix}"/> FCFA</span>
						</div>
					</div>
				</div>
			
			</c:forEach>
		</div>
		
		<div class="nouveautes-prev"></div>
		<div class="nouveautes-next"></div>
	
	</div>
</c:if>