package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.LessonView;

public interface LessonViewDao {

	boolean addViewLesson(LessonView lessonView);

	List<Long> getFormationLessonView(Long formationId, Long utilisateurId);

	boolean lessonIsView(Long utilisateurId, Long lessonId);

}
