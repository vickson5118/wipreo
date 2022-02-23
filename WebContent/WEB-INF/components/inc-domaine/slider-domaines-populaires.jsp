<c:if test="${!empty requestScope.listeHuitDerniersDomaine}">
	<!-- domaines populaires -->
	<div class="container domaines-populaires">
		<p>Les thèmes populaires</p>
	
		<div class="row slider-domaines-populaires">
		
			<c:forEach items="${requestScope.listeHuitDerniersDomaine}" var="domaine">
				<div class="col-md-12 domaine-card">
					<h1><a href="/formations/${domaine.titreUrl}"><c:out value="${domaine.titre}" /></a></h1>
				</div>
			</c:forEach>
	
			
	
		</div>
		
		<div class="domaines-populaires-prev"></div>
		<div class="domaines-populaires-next"></div>
	</div>
</c:if>