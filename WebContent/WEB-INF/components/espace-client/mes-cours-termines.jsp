<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Mes formations terminées | Wipreo</title>
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/espace-client/menu.css" type="text/css"/>
<link rel="stylesheet" href="/inc/css/espace-client/cours-termines.css" type="text/css" />
</head>
<body>

	<!-- Header -->
	<c:import url="/inc/other/header.jsp" />

	<!-- bar personnalisé -->
	<c:import url="menu.jsp" />
	
	<div class="container-fluid">
		<div class="container">
			
			<c:choose>
				<c:when test="${empty requestScope.listeCoursTermines}">
					
					<div class="row justify-content-center">
						<div class="col-md-8">
							
							<div class="redaction-icon"><i class="bi bi-journal-x"></i></div>
							<p class="text-redaction-vide">Aucune formation terminée</p>
							
						</div>
					</div>
				
				</c:when>
				<c:otherwise>
				
					<div class="row cours-favoris-container">
						
						<c:forEach items="${requestScope.listeCoursTermines}" var="paid">
							
							
							<div class="col-md-3 formation-item">
			
							<div class="card">
								<img src='<c:out value="${paid.formation.illustration}"/>' class="card-img-top" alt='<c:out value="${paid.formation.titre}"/>'>
								<div class="card-body">
										
									<h1 class="card-title"><a href=""><span class="cours-title"><c:out value="${paid.formation.titre}"/></span></a></h1>
									<div class="auteur">
										<span class="auteur-img"></span><span><c:out value="${paid.formation.auteur.prenoms}"/> <c:out value="${paid.formation.auteur.nom}"/></span>
									</div>
									<div class="formation-extra-container">
											<span style="font-size: 14px;">Note : </span> 
											<div class="rating" data-rate-value=<c:out value="${paid.formation.rating}"/>></div>
									</div>
								</div>
							</div>
						</div>
							
						</c:forEach>
						
					</div>
				
				</c:otherwise>
				</c:choose>
			
		</div>
	</div>

	<!-- Footer -->
	<c:import url="/inc/other/footer.jsp" />

	<c:import url="/inc/other/js.jsp" />
	<script src="/inc/js/mes-cours.js" type="text/javascript"></script>
	<script src="/inc/js/rater.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var rateOptions = {
				readonly: true
		}
		$(".rating").rate(rateOptions);
		</script>
</body>
</html>