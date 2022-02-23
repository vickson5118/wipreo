<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Mes formations | Wipreo</title>
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/espace-client/menu.css" type="text/css"/>
<link rel="stylesheet" href="/inc/css/espace-client/mes-cours.css" type="text/css"/>
<link rel="stylesheet" href="/inc/assets/progress-bar/loading-bar.min.css" type="text/css"/>
</head>
<body>

	<!-- Header -->
	<c:import url="/inc/other/header.jsp" />
	
	<!-- bar personnalisé -->
		<c:import url="menu.jsp" />
	
		<div class="container-fluid">
		

		<!-- les plus populaires -->
		<div class="container">
		
		<c:choose>
				<c:when test="${empty requestScope.listePaidFormation}">
					
					<div class="row justify-content-center">
						<div class="col-md-8">
							
							<div class="redaction-icon"><i class="bi bi-journal-x"></i></div>
							<p class="text-redaction-vide">Aucune formation disponible</p>
							
						</div>
					</div>
				
				</c:when>
				<c:otherwise>
				
					<div class="row cours-container">
					
						<c:forEach items="${requestScope.listePaidFormation}" var="paid">
							
							<div class="col-md-3 formation-item">

							<div class="card">
								<img src='<c:out value="${paid.formation.illustration}"/>' class="card-img-top" alt='<c:out value="${paid.formation.titre}"/>'>
								<div class="card-body">
									<h1 class="card-title">
										<a href="/apprendre/formations/${paid.formation.domaine.titreUrl}/${paid.formation.titreUrl}"><span class="cours-title"><c:out value="${paid.formation.titre}" /></span></a>
									</h1>
									<div class="auteur">
										<span class="auteur-img"></span><span><c:out value="${paid.formation.auteur.prenoms}" /> <c:out value="${paid.formation.auteur.nom}" /></span>
									</div>
									<div class="formation-extra-container">
										<span style="font-size: 14px;">Note : </span>
										<div class="rating" data-rate-value=<c:out value="${paid.formation.rating}"/>></div>
									</div>
									<div class="progression">
										<div class="ldBar" data-value="<c:out value="${paid.pourcentageLessonView}"/>"></div>
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
	<script src="/inc/assets/progress-bar/loading-bar.min.js" type="text/javascript"></script>
	<script src="/inc/js/rater.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var rateOptions = {
				readonly: true
		}
		$(".rating").rate(rateOptions);
		</script>
</body>
</html>