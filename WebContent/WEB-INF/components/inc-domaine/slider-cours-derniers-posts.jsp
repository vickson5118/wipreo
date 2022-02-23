<c:if test="${!empty requestScope.listeTroisDernieresFormation}">
	
	<div class="container derniers-posts">
	<p>Cours à l'affiche</p>



	<div class="row slider-derniers-posts">
	
		<c:forEach items="${requestScope.listeTroisDernieresFormation}" var="formation">
		
				<div class="col-md-12">
					<img src="${formation.illustration}" alt="<c:out value="${formation.titre}"/>" />
					<h1> 
						<a href="/formations/${formation.domaine.titreUrl}/${formation.titreUrl}">
							<span class="cours-title"><c:out value="${formation.titre}"/></span>
						</a>
					</h1>
					<div class="derniers-posts-infos">
					
						<p class="auteur">Par <c:out value="${formation.auteur.prenoms}"/> <c:out value="${formation.auteur.nom}"/> </p>
						<p class="formation-infos-extras">Derniere mise à jour <c:out value="${formation.dateCreation}"/> - <c:out value="${formation.nombreHeures}"/> heures au total</p>
						<span class="rating-content"><c:out value="${formation.rating}"/></span> <div class="rating" data-rate-value=<c:out value="${formation.rating}"/>> </div>
						<p class="prix"><c:out value="${formation.prix}"/> FCFA</p>
						
					</div>
					
				</div>
		
		</c:forEach>

	</div>
	<!-- slider-derniers-posts -->

	<div class="derniers-posts-prev"></div>
	<div class="derniers-posts-next"></div>

</div>
<!-- container derniers-posts -->


</c:if>