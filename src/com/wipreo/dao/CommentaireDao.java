package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Commentaire;

public interface CommentaireDao {

	boolean createCommentaire(Commentaire commentaire);

	Float getFormationRatingMoyenne(Long formationId);

	List<Commentaire> getFormationAllCommentaire(Long formationId);

	// Integer getFormationNombreCommentaire(Long formationId);

}
