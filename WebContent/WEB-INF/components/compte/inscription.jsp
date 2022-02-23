<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Inscrivez vous et profiter pleinement des formations | Wipreo</title>
		<meta charset="utf-8"/>
		<c:import url="/inc/other/css.jsp" />
		<link rel="stylesheet" href="/inc/css/compte/inscription.css" type="text/css"/>
		<!-- <meta name="google-signin-client_id" content="489730980535-1a1rv50cva8mn87fr3a46r6olrv82ptv.apps.googleusercontent.com"> -->
	</head>
	<body>
		
		<div class="container">				
			<div class="row">
				<div class="col-md-6 inscription-box">
			
					<div class="logo-compte">
						<a href="/"><img src="/inc/images/logo.png" alt="Wipreo" /></a>
					</div>
			
					<p class="inscription-title">Inscription</p>
					<p class="btn-sociaux">
						<button id="btn-facebook">FACEBOOK</button>
						<!--  <button id="btn-google">GOOGLE</button>
						<button id="btn-linkedin">LINKEDIN</button>-->
					</p>
					
					<p class="res-btn-sociaux">
						<button id="btn-facebook"></button>
						<button id="btn-google"></button>
						<button id="btn-linkedin"></button>
					</p>
			
					<div class="row justify-content-md-center">
			
						<div class="form-floating mb-3 col-md-9 inscription-info">
							<input type="text" class="form-control" id="prenoms" placeholder="Prénoms" name="prenoms"> 
							<label for="prenoms">Prénoms</label>
							<div class="error"></div>
						</div>
			
						<div class="form-floating col-md-9 mb-3 inscription-info">
							<input type="text" class="form-control" id="nom" placeholder="Nom" name="nom"> 
							<label for="nom">Nom</label>
							<div class="error"></div>
						</div>
			
						<div class="form-floating col-md-9 mb-3 inscription-info">
							<input type="email" class="form-control" id="email" placeholder="Adresse email" name="email"> 
							<label for="email">Adresse email</label>
							<div class="email-info">Assurez vous de saisir une adresse email valide, vous en aurez besoin pour l'activation de votre compte.</div>
							<div class="error"></div>
						</div>
			
						<p class="politique">
							En cliquant j'atteste que j'ai lu et j'accèpte <a href="">les
								conditions générales d'utilisation</a>
						</p>
			
						<div class="btn-inscription-content">
							<button id="btn-inscription-valide" class="mb-4">S'inscrire</button>
						</div>
			
						<p id="existe-compte">
							Vous avez déjà un compte ? <a href="/compte/login">Connectez-vous</a>
						</p>
					</div>
				</div>
			
				<div class="col-md-6 inscription-citation-box">
					<p class="citation align-middle">
						« LES ESPÈCES QUI SURVIVENT NE SONT PAS LES ESPÈCES LES PLUS FORTES,
						NI LES PLUS INTELLIGENTES, MAIS CELLES QUI S’ADAPTENT LE MIEUX AUX
						CHANGEMENTS. » <span class="citation-auteur">Charles Darwin</span>
					</p>
				</div>
			
			</div>
		</div><!-- Container -->
		
		<c:import url="/inc/other/js.jsp" />
		<script src="/inc/js/compte/inscription.js" type="text/javascript"></script>
	</body>
</html>