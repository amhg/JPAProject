package com.bezkoder.spring.datajpa.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
public class Tutorial {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	//TODO try with LAZY, change the positions btwn Join and ManyToOne
	@JoinColumn(name = "author_id",referencedColumnName = "id")
	@ManyToOne
	private Author author;

	public Tutorial() {

	}

	public Tutorial(String title, String description, boolean published) {
		this.title = title;
		this.description = description;
		this.published = published;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Tutorial{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", published=" + published +
				", author=" + author +
				'}';
	}
}
