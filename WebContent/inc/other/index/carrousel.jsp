<div class="container-fluid first-carrousel-container">
  
  
  <div class="row carrousel-last-formations">
  	
  	<c:forEach items="${requestScope.listeTroisDerniersFormation}" var="formation" varStatus="status">
  		
  		 <div class="col-md-12 one-carrousel-item">
	      <img src='<c:out value="${formation.illustration}"/>' alt='<c:out value="${formation.titre}"/>'>

	        <h1>
	        	<a href="/formations/<c:out value="${formation.domaine.titreUrl}"/>/<c:out value="${formation.titreUrl}"/>">
	        		<c:out value="${formation.titre}"/>
	        	</a>
	        </h1>
	        <div class="one-item-caption-bg">
				 <div class="one-item-caption-infos">
					<span class="auteur-name"><c:out value="${formation.auteur.prenoms}"/> <c:out value="${formation.auteur.nom}"/></span><br />
		        	<span><c:out value="${formation.auteur.fonction}"/></span>
		        	<div class="rating" data-rate-value=<c:out value="${formation.rating}"/>></div>
		        	<span class="prix"><c:out value="${formation.prix}"/> FCFA</span>
	      		</div>
      		</div>
	     
	    </div>
  		
  	</c:forEach>
    
  </div>
  
  	<div class="last-formation-prev"></div>
	<div class="last-formation-next"></div>
	 
</div>