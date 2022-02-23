<div class="domaines">

		<p>Nos domaines de formations</p>

		<div class="row domaine-container">
			
			<c:forEach items="${sessionScope.listeDomaine}" var="domaine">
				
				<div class="col-md-3 one-domaine">
					<div class="card">
						<img src='<c:out value="${domaine.illustration}"/>' class="card-img-top" alt="<c:out value="${domaine.titre}"/>"/>
						<h2><a href="/formations/<c:out value="${domaine.titreUrl}"/>" class="card-title"><span><c:out value="${domaine.titre}"/></span></a></h2>
					</div>
				</div>
			
			</c:forEach>

		</div>
		
		<!-- Apparait a -767px -->
		<div class="domaine-container-res">
			
			<c:forEach items="${sessionScope.listeDomaine}" var="domaine">
				
				<div class="one-domaine-res">
					<h2><a href="/formations/<c:out value="${domaine.titreUrl}"/>" class="card-title"><span><c:out value="${domaine.titre}"/></span></a></h2>
				</div>
			
			</c:forEach>

		</div>


</div>
<!-- domaines -->