<!-- Modal de creation d'une formation-->
<div class="modal fade" id="staticBackdropAddCertificat" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">Obténir un certificat</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="error text-center">Ces informations seront affichées sur votre certifcat et ne pourront plus changer.</div>
				<form>
				
					<div class="form-floating mb-3">
					  <input type="text" class="form-control" id="prenoms" name="prenoms" placeholder="Prenoms">
					  <label for="prenoms">Prenoms</label>
					</div>
					
					<div class="form-floating mb-3">
					  <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom">
					  <label for="nom">Nom</label>
					</div>
					
					<div class="form-floating mb-3">
					  <input type="text" class="form-control" id="dateNaissance" name="dateNaissance" placeholder="Date de naissance">
					  <label for="dateNaissance">Date de naissance</label>
					</div>
				
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"data-bs-dismiss="modal">Annuler</button>
				<button type="button" class="btn btn-primary" id="btn-confirm-terminate-formation">Terminer la formation</button>
			</div>
		</div>
	</div>
</div>