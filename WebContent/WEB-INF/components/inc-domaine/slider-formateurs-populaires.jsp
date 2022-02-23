<div class="container formateurs-populaires">
	<p>Formateurs populaires</p>
	<div class="row slider-formateurs-populaires">
	
	<c:forEach items="${requestScope.listeHuitDerniersAuteur}" var="auteur">
	
		<div class="col-md-12 one-formateur">
			<div class="image-formateur"><img src="<c:out value="${auteur.photo}"/>" alt="" /></div>
				<span class="nom"><c:out value="${auteur.prenoms}"/> <c:out value="${auteur.nom}"/></span><br /> 
				<span class="fonction"><c:out value="${auteur.fonction}"/></span><br />
				<a href="/formateur/${auteur.profil}"></a>
				<span class="rating-number"><c:out value="${auteur.rating}"/></span><div class="rating" data-rate-value=<c:out value="${auteur.rating}"/>></div>
				<br /><!--  <span class="number-participants">45236 participants</span><br /> -->
				<span class="number-cours"><c:out value="${auteur.nombreFormation}"/> cours</span>
		</div>
	
	</c:forEach>

		

	</div>
	
	<div class="formateurs-prev"></div>
	<div class="formateurs-next"></div>
</div>