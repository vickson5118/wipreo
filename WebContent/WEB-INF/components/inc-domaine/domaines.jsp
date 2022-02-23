<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/assets/slick/slick-min.css" type="text/css"/>
		<link rel="stylesheet" href="/inc/assets/slick/slick-theme.css" type="text/css"/>
		<!--  <link rel="stylesheet" href="/inc/css/carrousel-index.css" type="text/css"/>-->
		<link rel="stylesheet" href="/inc/css/formation/domaines.css" type="text/css"/>
	</head>
	<body>
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
		<div class="container-fluid">
			<div class="container">
				<h1 class="domaine-title"><c:out value="${requestScope.domaine.titre}"/></h1>
				
				<div class="domaine-infos row">
				
				<div class="domaine-description col-md-8">
					<c:out value="${requestScope.domaine.description}"/>
				</div>
				
				<img src="${requestScope.domaine.illustration}" alt="<c:out value="${requestScope.domaine.titre}"/>" class="domaine-image col-md-4"/>
				</div>
				
				
			</div><!-- Container -->
			
			<!-- Affiche les formations les plus cotés -->
			<c:import url="slider-cours-populaire.jsp"/>
			<!-- Affiche les dernières formations -->
			<c:import url="slider-cours-derniers-posts.jsp"/>
			<!-- Afiche les domaines de formations populaires -->
			<c:import url="slider-domaines-populaires.jsp"/>
			<!-- Affiche les meilleurs formateurs -->
			<c:import url="slider-formateurs-populaires.jsp"/>
			<!-- Les autres formations -->
			<c:import url="slider-other-formations.jsp"/>
			
		</div><!-- container-fluid -->
		
		<!-- Footer -->
			<c:import url="/inc/other/footer.jsp"/>
		
		<c:import url="/inc/other/js.jsp" />
		<script type="text/javascript" src="/inc/assets/slick/slick-min.js"></script>
		<script src="/inc/js/formation/domaines.js" type="text/javascript"></script>
		<script src="/inc/js/rater.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		
		var rateOptions = {
				readonly: true
		}
		$(".rating").rate(rateOptions)
		</script>
	</body>
</html>