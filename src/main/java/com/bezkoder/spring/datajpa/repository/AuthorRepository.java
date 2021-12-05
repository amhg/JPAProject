package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);


}
