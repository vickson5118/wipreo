<header>

<div class="checkbtn"><i class="bi bi-list ouvrir"></i><i class="bi bi-x fermer"></i></div>

<div class="nav-item res-nav-basket">
	<a class="nav-link" href="/panier"><i class="bi bi-cart4"></i></a> 
	<c:if test="${sessionScope.panierCount > 0}"><span class="gbadge"><c:out value="${sessionScope.panierCount}" /></span></c:if>
</div>

<div class="nav-item res-login-btn">
	
	<c:choose>
		<c:when test="${sessionScope.utilisateur.id != null}">
			
			<c:choose>
				<c:when test="${sessionScope.utilisateur.photo == null}">
					<div class="menu-profil-picture"><c:out value="${sessionScope.utilisateur.initial}" /></div>
				</c:when>
				<c:otherwise>
					<div class="menu-profil-picture"><img src="${sessionScope.utilisateur.photo}" alt="Photo de profil <c:out value="${sessionScope.utilisateur.prenoms}" />" /></div>
				</c:otherwise>
			</c:choose>
			
			
		</c:when>
		<c:otherwise>
			<a class="nav-link" href="/compte/login"><i class="bi bi-person-fill"></i></a> 
		</c:otherwise>
	</c:choose>

	
</div>

	<!-- Affichage du sous menu lors des petits ecrans -->
	<c:if test="${sessionScope.utilisateur.id != null}">
		 <div class="res-user-connect-container">
			<a href="/espace-client/mes-cours">Mon espace</a> 
			<a href="/membre/profil/informations-generales">Mon profil</a>
			<a href="/membre/historique-achats">Historique des achats</a>
			<a href="/deconnexion">Deconnexion</a>
		</div>
	</c:if>

<a class="logo-container" href="/"> <img src="/inc/images/logo.png"
			alt="logo Wipreo" width="160" height="50">
		</a>

	<div class="container menu-container">

		<div class="other-infos-header">

			<div class="espace-client-header">
				<span>
						<i class="bi bi-person-fill"></i>
						<c:choose>
							<c:when test="${sessionScope.utilisateur.id != null}"><c:out value="${sessionScope.utilisateur.nom}" /></c:when>
							<c:otherwise>Espace client</c:otherwise>
						</c:choose> 
					</span>
					
					<c:choose>
					
						<c:when test="${sessionScope.utilisateur.id == null}">
							<div id="container-connexion" class="container-connexion">
								<button id="btn-facebook-nav">FACEBOOK</button>
								  <form>
									<p id="identifiez-vous">Identifiez-vous</p>
									<input type="email" placeholder="Email" id="email" name="email" required> 
									<input type="password" placeholder="Mot de passe" id="password" name="password" required>
									<button id="user-connect" type="button">Se connecter</button>
									<a href="/compte/new-password" id="forget-pass">Mot de passe oublié?</a> 
									<a href="/compte/inscription" id="create-compte">Créer un compte</a>
								</form>
							</div>
						</c:when>
						<c:otherwise>
						 <div class="user-connect-container">
							<a href="/espace-client/mes-cours">Mon espace</a> 
							<a href="/membre/profil/informations-generales">Mon profil</a>
							<a href="/membre/historique-achats">Historique des achats</a>
							<a href="/deconnexion">Deconnexion</a>
						</div>
					</c:otherwise>
					</c:choose>
				
				
			</div>

		</div>
		<!-- fin other-infos-header -->
		<c:import url="/inc/other/nav.jsp" />
		
		<div class="nav-basket">
			<a class="nav-link" href="/panier"><i class="bi bi-cart4"></i></a>
			<c:if test="${sessionScope.panierCount > 0}"><span class="gbadge"><c:out value="${sessionScope.panierCount}" /></span></c:if>
		</div>
	</div>
	
</header>