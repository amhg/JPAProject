package com.bezkoder.spring.datajpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.datajpa.model.Tutorial;
import org.springframework.data.jpa.repository.Query;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	List<Tutorial> findByAuthorId(Long authorId);

	@Query(value = "SELECT * FROM tutorials WHERE title in :titleList", nativeQuery = true)
	List<Tutorial> findByTitleContaining(List<String> titleList);

	/**
	 * Using NaviteQuery JPQL
	 * */
	@Query("select t from Tutorial t where t.title=:titleName")
	List<Tutorial> findByTitleV2(String titleName);

	/*

	//TODO write a query with/without using NaviteQuery JPQL
	@Query("select t from Tutorial t where t.title in (:titles)")
	List<Tutorial> findByTitleV3List(List<String> titles);*/
}
