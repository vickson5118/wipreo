<nav style="z-index: 9">
	<ul class="nav justify-content-end">
		<li class="nav-item"><a class="nav-link" href="/">Accueil</a></li>
		<li class="nav-item nav-domaines" style="cursor: pointer;"><a class="nav-link">Domaines de formations</a>
			<ul class="smenu">
				<c:forEach items="${sessionScope.listeDomaine}" var="domaine">
					<li><a href="/formations/<c:out value="${domaine.titreUrl}"/>"><c:out value="${domaine.titre}"/></a></li>
				</c:forEach>
			</ul>
		</li>
		<li class="nav-item"><a class="nav-link" href="/contactez-nous">Contactez-nous</a></li>
		
	</ul>
</nav>