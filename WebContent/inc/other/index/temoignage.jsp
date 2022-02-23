<c:if test="${!empty requestScope.listeTemoignage}" >
	
	<!-- Temoignages -->
<div class="container temoignages">

	<p>Ils parlent de nous</p>
	<div class="row temoignage-slider justify-content-md-center">
		
		<c:forEach items="${requestScope.listeTemoignage}" var="temoignage">
			
			<div class="col-md-12 temoignage">
			<p class="temoignage-texte"><c:out value="${temoignage.texte}"/></p>
			
			<div class="temoignage-infos">
				<div class="temoignage-image-profil"></div>
				<div class="temoignage-nom"><c:out value="${temoignage.prenoms}"/> <c:out value="${temoignage.nom}"/></div>
				<div class="temoignage-fonction"><c:out value="${temoignage.fonction}"/></div>
			</div>
		</div>
		
		</c:forEach>
		
	</div>

	<div class="temoignage-prev"></div>
	<div class="temoignage-next"></div>

</div>
<!-- Temoignages -->
	
</c:if>

