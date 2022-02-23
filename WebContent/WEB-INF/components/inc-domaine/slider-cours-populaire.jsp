
<!-- les plus populaires -->
<div class="container cours-populaire">
	<p>Les plus populaires</p>

	<div class="row slider-cours-populaire">
	
	<c:forEach items="${requestScope.listeHuitDernieresFormation}" var="formation">
	
		<div class="col-md-12 formation-item">
			
				<div class="card">
					<img src='<c:out value="${formation.illustration}"/>' class="card-img-top" alt='<c:out value="${formation.titre}"/>'>
					<div class="card-body">
						<h1 class="card-title"><a href="/formations/${formation.domaine.titreUrl}/${formation.titreUrl}"><span class="cours-title"><c:out value="${formation.titre}"/></span></a></h1>
						<div class="auteur">
							<span><c:out value="${formation.auteur.prenoms}"/> <c:out value="${formation.auteur.nom}"/></span>
						</div>
						<div class="formation-extra-container">
								<span class="fmark">Note : </span> 
								<div class="rating" data-rate-value=<c:out value="${formation.rating}"/>></div>
						</div>
						<div class="formation-prix"><b><c:out value="${formation.prix}"/> FCFA</b> </div>
						<!--  <div class="formation-promo"><b><c:out value="${formation.prix}"/> FCFA</b> </div>-->
					</div>
				</div>
		</div>
	
	</c:forEach>

	</div>
	
	<div class="cours-populaires-prev"></div>
	<div class="cours-populaires-next"></div>


</div>
<!-- populaire -->