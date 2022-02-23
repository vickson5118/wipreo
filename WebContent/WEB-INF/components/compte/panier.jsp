<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Panier | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/compte/panier.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			
			<div class="panier-header-container">
				<div class="container">Panier</div>
			</div>
			
			<div class="container">
			
				<div class="row justify-content-center">
					<c:choose>
						<c:when test="${empty requestScope.listePanier}">
							<div class="col-md-8" >
						
							<div class="panier-icon"><i class="bi bi-cart-fill"></i></div>
							<p class="text-panier-vide">Votre panier est vide</p>
							
					</div>
						</c:when>
						<c:otherwise>
							
							<div class="col-md-9 one-formation-panier-container">
							
								
								<c:forEach items="${requestScope.listePanier}" var="panier">
								
									<div class="one-formation-panier">
										<div class="formation-infos">
											<img src="${panier.formation.illustration}" alt="${panier.formation.titre}" />
											<div class="formation-infos-content">
												<h1 class="formation-title"><a href="/formations/<c:out value="${panier.formation.domaine.titreUrl}" />/<c:out value="${panier.formation.titreUrl}" />"><c:out value="${panier.formation.titre}" /></a></h1>
												<p class="formateur-reference">Par <c:out value="${panier.utilisateur.prenoms}" /> <c:out value="${panier.utilisateur.nom}" /></p>
												<p class="date-publication">Publié le <c:out value="${panier.formation.dateCreation}" /></p>
												<p class="mark-container"><span class="formation-hours"><c:out value="${panier.formation.nombreHeures}" /> heures</span> | <span class="mark-number"><c:out value="${panier.formation.rating}" /></span> </p>
											
											</div>
										</div>
										
										<div class="formation-panier-price-delete">
											<button value="${panier.id}" class="bi bi-x-lg btn-delete-formation-panier" title="Supprimer du panier" type="button"></button>
									<div class="one-formation-panier-price"><c:out value="${panier.formation.prix}" /> <span class="formation-devise">FCFA</span></div>
										</div>
								</div>
								
								
								</c:forEach>
							</div>
							<div class="col-md-3 total-container">
								<c:set var="panierTotal" value="0" scope="page"/>
								<div class="total-panier-container">
									<p>Total</p>
									<p class="panier-total"><span>
										<c:forEach items="${requestScope.listePanier}" var="panier">
											<c:set var="panierTotal" value="${panierTotal + panier.formation.prix}" scope="page"/>
										</c:forEach>
										<c:out value="${panierTotal}"/>
									</span> <span>FCFA</span></p>
									<button type="button" title="Payer maintenant" class="btn-panier-payment" id="process_payment">Valider les achats</button>
								</div>
							</div>
							
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<c:if test="${!empty requestScope.listeFormation}">
			
				<div class="interresting-courses">
				<div class="container">
					<h1>Les formations qui pourraient vous interesser</h1>
					
					<div class="row">
					
						<c:forEach items="${requestScope.listeFormation}" var="formation">
								
						<div class="col-md-3 formation-item">

								<div class="card">
									<img src='<c:out value="${formation.illustration}"/>' class="card-img-top" alt='<c:out value="${formation.titre}"/>'>
									<div class="card-body">
										<h1 class="card-title">
											<a href="/formations/<c:out value="${formation.domaine.titreUrl}"/>/<c:out value="${formation.titreUrl}"/>"><span class="cours-title"><c:out value="${formation.titre}" /> </span></a>
										</h1>
										<div class="auteur">
											<span class="auteur-img"></span><span><c:out
													value="${formation.auteur.prenoms}" /> <c:out
													value="${formation.auteur.nom}" /></span>
										</div>
										<div class="formation-extra-container">
											<span class="fmark">Note : </span>
											<div class="rating"
												data-rate-value=<c:out value="${formation.rating}"/>></div>
										</div>
										<div class="formation-prix">
											<b><c:out value="${formation.prix}" /> FCFA</b>
										</div>
										<!--  <div class="formation-promo"><b><c:out value="${formation.prix}"/> FCFA</b> </div>-->
									</div>
								</div>
							</div>
						
						</c:forEach>
					
					</div>
					
				
				</div>
			</div>	
			</c:if>
			
			
			<div id="payment_result"> 
 
			    <form id="info_paiement">
			        <input type="hidden"  id="amount" value="${pageScope.panierTotal}">
			
			        <input type="hidden" id="currency" value="CFA">
			
			        <input type="hidden" id="designation" value="Formation wipreo">
			        
			    </form>
			 </div>  
			
		</div><!-- Container-fluid -->
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script type="text/javascript" src="/inc/js/compte/panier.js"></script>
		<script src="/inc/js/rater.min.js" type="text/javascript"></script>
		<script charset="utf-8" src="https://www.cinetpay.com/cdn/seamless_sdk/latest/cinetpay.prod.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			var rateOptions = {
				readonly : true
			}
			$(".rating").rate(rateOptions);
		</script>
	</body>
</html>