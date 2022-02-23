<!-- Others formations -->
<div class="container other-formations-container" style="position: relative;">
	<p>Tous les cours de la categorie <b><c:out value="${requestScope.domaine.titre}"/></b></p>
	<div class="row other-formations" id="other-formations">
	
		
		<c:forEach items="${requestScope.listeFormation}" var="formation">
			<div class="col-md-3 formation-item">
				
					<div class="card">
						<img src='<c:out value="${formation.illustration}"/>' class="card-img-top" alt='<c:out value="${formation.titre}"/>'>
						<div class="card-body">
							<h1 class="card-title"><a href=""><span class="cours-title"><c:out value="${formation.titre}"/></span></a></h1>
							<div class="auteur">
								<span><c:out value="${formation.auteur.prenoms}"/> <c:out value="${formation.auteur.nom}"/></span>
							</div>
							<div class="formation-extra-container">
									<span class="fmark">Note : </span> 
									<span class="frating-number"><c:out value="${formation.rating}"/> / 5</span>
							</div>
							<div class="formation-prix"><b><c:out value="${formation.prix}"/> FCFA</b> </div>
							<!--  <div class="formation-promo"><b><c:out value="${formation.prix}"/> FCFA</b> </div>-->
						</div>
					</div>
			</div>
		</c:forEach>

	</div>

	<div class="pagination">
		<c:forEach begin="1" end="${requestScope.nombrePage}" var="count">
			<button value="${count}-${requestScope.domaine.titreUrl}" class="link-page"><c:out value="${count}" /></button>
		</c:forEach>
		
	</div>

</div>
<!-- Container other formations -->