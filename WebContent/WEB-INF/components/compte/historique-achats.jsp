<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Historique des achats | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/compte/historique.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			
			<div class="historique-header">
				<div class="container">
					<p>Historique des achats</p>
				</div>
			</div>
		
			<div class="container">
				<div class="row">
					
					<c:choose>
						<c:when test="${empty requestScope.listeFacture}">
						
							<div class="col-md-8 justify-content-center">
								<div class="historique-icon"><i class="bi bi-receipt"></i></div>
								<p class="text-historique-vide">Vous n'avez encore effectué aucun achat de formation.</p>
								<a href="/" type="button" class="btn-begin-purchase">Commencer les achats</a>
							</div>
							
						</c:when>
						<c:otherwise>
							
							<table class="table">
							  <thead>
							    <tr>
							      <th scope="col">N°</th>
							      <th scope="col" class="col-designation">N° Facture</th>
							      <th scope="col" class="col-date">Date</th>
							      <th scope="col" class="col-prix">Prix Total</th>
							      <th scope="col"></th>
							      <th scope="col"></th>
							    </tr>
							  </thead>
							  <tbody>
							    
							    <c:forEach items="${requestScope.listeFacture}" var="facture" varStatus="status">
							    		
							    		<tr>
									      <th scope="row" class="fcount"><c:out value="${status.count}"/></th>
									      <td class="fdesignation"><c:out value="${facture.designation}"/></td>
									      <td class="fdate"><c:out value="${facture.dateCreation}"/></td>
									      <td class="fprix"><c:out value="${facture.prixTotal}"/> FCFA</td>
									       <td class="check-recu"><a href="/membre/historique-achats/<c:out value="${facture.designation}"/>">Reçu</a></td>
									       <td><a href="/wipreo/pdf/${facture.pdf}" class="download-recu">Télécharger</a></td>
									    </tr>
							    		
							    </c:forEach>
							  
							  </tbody>
							</table>
							
							
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</div>
		
		<!-- Footer -->
		<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/compte/historique.js" type="text/javascript"></script>
	</body>
</html>