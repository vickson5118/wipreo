<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Se connecter | Wipreo</title>
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/compte/login.css" type="text/css" />
</head>
<body>


	<div class="container-fluid">
		<a class="logo-container" href="/"> <img
			src="/inc/images/logo.png" alt="logo Wipreo" width="170" height="70">
		</a>

		<div class="container">

			<div class="login-container">

				<p class="welcome">Bienvenue</p>
				<p>Connectez-vous et apprenez en toute simplicité</p>

				<div class="form-content">
					<div>Login</div>
					<p>
						Vous n'avez pas encore de compte ? <a href="/compte/inscription">Créer un compte</a>
					</p>

					<form>

						<div class="form-floating mb-3">
							<input type="email" class="form-control" id="email"
								placeholder="Adresse mail" name="email"> <label
								for="email">Adresse mail</label>
						</div>

						<div class="form-floating mb-3">
							<input type="password" class="form-control" id="password"
								placeholder="Mot de passe" name="password"> <label for="password">Mot
								de passe</label>
						</div>

						<div class="form-check">
							<input class="form-check-input" type="checkbox" value=""
								id="remenber-me"> <label class="form-check-label"
								for="remenber-me"> Se souvenir de moi </label>
						</div>

						<p class="password-forget-link">
							<a href="/compte/new-password">Mot de passe oublié ?</a>
						</p>

						<button id="user-connect" type="button">Se connecter</button>

					</form>
					
					<p class="other-connect">Ou connectez-vous avec : 
						<span class="btn-container">
							<button id="btn-facebook"><img src="/inc/images/icones/compte/facebook.png" alt="Facebook"/></button>
							<button><img src="/inc/images/icones/compte/linkedin.png" alt="LinkedIn"/></button>
							<button><img src="/inc/images/icones/compte/google.png" alt="Google"/></button>
						</span>
						</p>

				</div>

			</div>

		</div>
	</div>

	<c:import url="/inc/other/js.jsp" />
</body>
</html>