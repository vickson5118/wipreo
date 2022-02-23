<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Ameliorer votre expérience | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/compte/preferences.css" type="text/css"/>
	</head>
	<body>
		
		<div class="container-fluid">
			<div class="container">
				
				<div class="logo-compte">
					<a href="/"><img src="/inc/images/logo.png" alt="Wipreo" /></a>
				</div>
				
				<p class="welcome">Bienvenue sur Wipreo</p>
				<p>Ameliorer votre expérience en repondant aux questions suivantes</p>
					
				<div class="objectif-container">
					
					<h1><span>1</span> Quel est votre objectif principal ?</h1>
						
					<div class="objectifs">
					
						
						<c:forEach items="${requestScope.listeObjectif}" var="objectif">
								<button class="objectif-card <c:if test="${objectif.id == sessionScope.objectifId}">objectif-actif</c:if>" value="${objectif.id}">
									<p><c:out value="${objectif.type}"/></p>
								</button>
						</c:forEach> 
						
					</div>
				</div>
				
				<div class="domaine-container">
					
					<h1><span>2</span> Quels domaines vous intéressent ?</h1>
					
						
					<div class="domaines">
						<p>Choississez un domaine de formation et nous pourrions ainsi vous recommander des contenus plus adaptés.</p>
						<c:forEach items="${sessionScope.listeDomaine}" var="domaine">
							<button class="domaine-type <c:if test="${domaine.id == sessionScope.domaineFavoris}">domaine-actif</c:if>" value="${domaine.id}"><c:out value="${domaine.titre}"/></button>
						</c:forEach>
					</div>
				</div>
				
				<a href="/" class="btn-domaine-type-valide">Valider</a>
				
		</div><!-- container -->
		</div><!-- container-fluid -->
		
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/compte/preferences.js" type="text/javascript"></script>
	</body>
</html>