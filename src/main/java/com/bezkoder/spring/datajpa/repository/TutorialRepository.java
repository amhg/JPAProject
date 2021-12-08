package com.bezkoder.spring.datajpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.datajpa.model.Tutorial;
import org.springframework.data.jpa.repository.Query;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	@Query(value = "select t.description, t.title, t.author_id, a.name as AuthorName, a.age from public.tutorials as t\n" +
			"inner join public.author as a \n" +
			"on t.author_id = a.id\n" +
			"where a.id = 1", nativeQuery = true)
	List<Tutorial> findByAuthorId(Long authorId);

	@Query(value = "select t from Tutorial t where t.author.id=:authorId")
	List<Tutorial> findByAuthorIdV2(Long authorId);

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
