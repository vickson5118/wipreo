<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Facture | Wipreo</title>
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/compte/facture-edit.css" type="text/css" />
</head>
<body>

	<!-- Header -->
	<c:import url="/inc/other/header.jsp" />

	<div class="container-fluid">
	
		<c:forEach items="${requestScope.listePanierPaid}" var="panierPaid" begin="0" end="0">
			<c:set var="factureDesignation" value="${panierPaid.facture.designation}" scope="page"></c:set>
			<c:set var="factureDate" value="${panierPaid.facture.dateCreation}" scope="page"></c:set>
		</c:forEach>

		<div class="facture-edit-header">
			<div class="container">
				<p>Reçu</p>
			</div>
		</div>
		<div class="container">

			<div class="recu-container">
				<div class="infos-generales">

					<h1>Wipreo, Inc</h1>
					<div>
						Boulevard Valery Giscard d'Estaing (Face ORCA DECO) <br />
						Marcory, Immeuble Kalimba, 3eme etage
					</div>
					<p>
						<a href="https://wipreo.com">Wipreo</a>
					</p>

					<p class="user-sold">
						Vendu à : <b><c:out value="${sessionScope.utilisateur.prenoms}"/> <c:out value="${sessionScope.utilisateur.nom}"/></b>
					</p>
				</div>

				<div class="facture-infos">
					<div>
						<b>Date :</b> <c:out value="${pageScope.factureDate}"/>
					</div>
					<p>
						<b>N° de facture :</b> <c:out value="${pageScope.factureDesignation}"/>
					</p>
				</div>

			</div>

			<div class="facture-content">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">N°</th>
							<th scope="col">Titre de la formation</th>
							<th scope="col" class="col-auteur">Auteur</th>
							<th scope="col">Prix</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.listePanierPaid}" var="panierPaid" varStatus="status">
							<tr>
								<th scope="row"><c:out value="${status.count}"/></th>
								<td><c:out value="${panierPaid.formation.titre}"/></td>
								<td class="row-auteur"><c:out value="${panierPaid.formation.auteur.prenoms}"/> <c:out value="${panierPaid.formation.auteur.nom}"/></td>
								<td><c:out value="${panierPaid.formation.prix}"/> FCFA</td>
								<c:set var="facturePrixTotal" value="${facturePrixTotal+panierPaid.formation.prix}"></c:set>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="total-container">
					Total réglé : <b><c:out value="${facturePrixTotal}"/> FCFA</b>
				</div>
				
			</div>

		</div>

	</div>

	<!-- Footer -->
	<c:import url="/inc/other/footer.jsp" />

	<c:import url="/inc/other/js.jsp" />
</body>
</html>