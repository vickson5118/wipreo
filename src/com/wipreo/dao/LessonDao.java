package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Lesson;

public interface LessonDao {

	boolean creerLesson(Lesson lesson);

	List<Lesson> getAllLesson(Long formationId);

	boolean deleteLesson(Long lessonId);

	boolean updateTitre(Lesson lesson);

	boolean updateLesson(Lesson lesson);

	String getLessonSource(long parseLong);

	Long getLessonId(String sourceUrl);

}
