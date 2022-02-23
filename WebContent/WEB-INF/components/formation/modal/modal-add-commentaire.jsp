<!-- Modal de creation d'une formation-->
<div class="modal fade" id="staticBackdropAddCommentaire" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">Ajouter un commentaire</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form>
				
					<div class="rating-container">
						<div class="rating"></div>
						<div class="error"></div>
					</div>
				
					<div class="mb-3">
					  <label for="avis" class="form-label">Votre avis</label>
					  <textarea class="form-control" id="avis" rows="5" name="avis"></textarea>
					  <div class="error"></div>
					</div>
				
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"data-bs-dismiss="modal">Annuler</button>
				<button type="button" class="btn btn-primary" id="btn-confirm-add-commentaire">Ajouter le commentaire</button>
			</div>
		</div>
	</div>
</div>