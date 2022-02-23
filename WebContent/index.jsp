<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Formations en ligne - Apprenez à votre rythme | Wipreo</title>
		<c:import url="/inc/other/css.jsp" />
		 <!--  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick-theme.min.css" integrity="sha512-17EgCFERpgZKcm0j0fEq1YCJuyAWdz9KUtv1EjVuaOz8pDnh/0nZxmU6BBXwaaxqoi9PQXnRWqlcDB027hgv9A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.css" integrity="sha512-yHknP1/AwR+yx26cB1y0cjvQUMvEa2PFzt1c9LlS4pRQ5NOTZFWbhBig+X9G9eYW/8m0/4OXNx8pxJ6z57x0dw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
		-->
		<link rel="stylesheet" href="/inc/assets/slick/slick-min.css" />
		<link rel="stylesheet" href="/inc/assets/slick/slick-theme.css" />
		<link rel="stylesheet" href="/inc/css/index/carrousel-index.css" />
		<link rel="stylesheet" href="/inc/css/index/index.css" />
	</head>
	<body id="index">
		
		<!-- Header -->
		<c:import url="/inc/other/header.jsp" />
		
					
			<!-- Carrousel haut -->
			<c:import url="/inc/other/index/carrousel.jsp" />
			
			<div class="container-fluid" id="index">
			
				<div class="container">
				
					<!-- Carrousel affichant les top formations -->
					<c:import url="/inc/other/index/carrousel-coups-coeur.jsp" />
					
					<!-- Carrousel affichant les nouvelles formations -->
					<c:import url="/inc/other/index/carrousel-nouveautes.jsp" />
					
					<!-- domaines du site -->
				<c:import url="/inc/other/index/domaines-index.jsp" />
					
				</div><!-- container -->
				
				<!-- pub du site -->
				<c:import url="/inc/other/index/pub.jsp" />
				
				<!--Témoignages des clients -->
				<c:import url="/inc/other/index/temoignage.jsp" />
				
			</div><!-- container-fluid -->
			
			<!-- Footer -->
			<c:import url="/inc/other/footer.jsp"/>		
			
		
		<c:import url="/inc/other/js.jsp" />
		<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.js" integrity="sha512-HGOnQO9+SP1V92SrtZfjqxxtLmVzqZpjFFekvzZVWoiASSQgSr4cw9Kqd2+l8Llp4Gm0G8GIFJ4ddwZilcdb8A==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		 -->
		<script type="text/javascript" src="/inc/assets/slick/slick-min.js"></script>
		<script src="/inc/js/index.js" type="text/javascript"></script>
		<script src="/inc/js/rater.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		
			var rateOptions = {
				readonly: true
			}
			$(".rating").rate(rateOptions);
		
		</script>
	</body>
</html>