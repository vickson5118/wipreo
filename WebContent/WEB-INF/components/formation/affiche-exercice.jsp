<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Exercez-vous et devenez meilleur | Exercice - ${requestScope.exercice.titre} | Wipreo</title>
<meta charset="utf-8" />
<c:import url="/inc/other/css.jsp" />
<link rel="stylesheet" href="/inc/css/formation/affiche-exercice.css" type="text/css" />
</head>
<body>

	<!-- Header -->
	<c:import url="/inc/other/header.jsp" />
	
	
		<div class="container-fluid">
			<div class="container">
			
				<div class="row">
					<div class="col-md-8 offset-md-2">
					
						<div class="exercice-title-container">
							<h1>Quiz - <c:out value="${sessionScope.exercice.titre}" /> </h1>
						</div>
						
						<div class="exercice-competence-container">
							<c:out value="${sessionScope.exercice.competence}" />
						</div>
						
						
						<div class="btn-return-formation-container">
							<a href="/apprendre/formations/${sessionScope.titreUrlDomaine}/${sessionScope.titreUrlFormation}" class="btn-return-formation" type="submit">Retourner à la formation</a>
						</div>
						
						<c:if test="${requestScope.exercicePassed != null}">
						
							<div class="affiche-info">
								<c:choose>
									<c:when test="${requestScope.exercicePassed.valide}">
										<div class="text-success">Bravo vous avez reussi le quiz.</div>
									</c:when>
									<c:otherwise>
										<div class="text-error">Désolé vous n'avez pas reussi le quiz.</div>
									</c:otherwise>
								</c:choose>
							</div>
							
						</c:if>
						
						
							<form method="post" id="form-exercice">
								<div class="questions-container">
								<c:forEach items="${requestScope.listeQuestion}" var="question" varStatus="status">
								
								<div class="question-item">
								
									<h3>${status.count} - <span class="question-libelle"><c:out value="${question.libelle}" /></span></h3>
									<div class="question-id" style="display: none;"><c:out value="${question.id}" /></div>
									<div class="reponse-container">
									
										<c:forEach items="${requestScope.listeReponse}" var="reponse">
										<c:if test="${reponse.questionParent.id == question.id}">
										
											<div class="one-reponse">
												<c:choose>
													<c:when test="${!requestScope.exercicePassed.valide}">
														<input type="radio" name="reponse-check-${question.id}" class="reponse-check" id="${reponse.id}" <c:forEach items="${requestScope.listeReponseCheck}" var="check"><c:if test="${check.reponseChecked.id == reponse.id}">checked="checked"</c:if></c:forEach>/> 
													</c:when>
													<c:otherwise>
														<input type="radio" class="disabled-check" <c:forEach items="${requestScope.listeReponseCheck}" var="check"><c:choose><c:when test="${check.reponseChecked.id == reponse.id}">checked="checked"</c:when> <c:otherwise>disabled="disabled"</c:otherwise> </c:choose></c:forEach>/> 
													</c:otherwise>
												</c:choose>
												
												<label for="${reponse.id}" class="reponse-label"> 
													<span class="dot"></span> 
													<span class="label-content"><c:out value="${reponse.libelle}" /></span>
												</label>
											</div>
											
										</c:if>
									</c:forEach>
									</div>
									
									</div>
									
								</c:forEach>
									
										<c:if test="${requestScope.exercicePassed == null}">
											
											<!-- 
											<div class="confirm-finish-exercice-container">
												<input type="checkbox" name="finish" id="finish"/>
												<label for="finish">Etes-vous de vouloir soumettre vos reponses ?</label>
											</div>
											 -->
										
												<div class="btn-terminate-container">
													<button class="btn-send-correction" type="submit">Soumettre pour correction</button>
												</div>
										</c:if>
										
											<div class="btn-return-formation-container">
													<a href="/apprendre/formations/${sessionScope.titreUrlDomaine}/${sessionScope.titreUrlFormation}" class="btn-return-formation" type="submit">Retourner à la formation</a>
												</div>

						</div>
							</form>
						
					
						
						
					</div>
				</div>
				
				
			</div>
		</div>
	<!-- Footer -->
	<c:import url="/inc/other/footer.jsp" />

	<c:import url="/inc/other/js.jsp" />
	<script src="/inc/js/formation/affiche-exercice.js" type="text/javascript"></script>
</body>
</html>