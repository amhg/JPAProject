package com.bezkoder.spring.datajpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bezkoder.spring.datajpa.exceptions.TutorialNotFoundException;
import com.bezkoder.spring.datajpa.model.Author;
import com.bezkoder.spring.datajpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.datajpa.model.Tutorial;
import com.bezkoder.spring.datajpa.repository.TutorialRepository;

@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;

	@Autowired
	AuthorRepository authorRepository;

	/**
	 * Using Native Query
	 */
	@PostMapping("/bookList")
	public ResponseEntity<List<String>> getFilteredTitles(@RequestBody List<String> titles){

		List<String> tutorialsName = tutorialRepository.findByTitleContaining(titles)
				.stream()
				.map(Tutorial::getDescription)
				.collect(Collectors.toList());

		return new ResponseEntity<>(tutorialsName, HttpStatus.OK);

	}

	@GetMapping("/bookList/{title}")
	public ResponseEntity<List<Tutorial>> getFilteredTitle(@PathVariable("title") String title){

		List<Tutorial> tutorials = tutorialRepository.findByTitleV2(title);
		if(tutorials.size() == 0){
			throw new TutorialNotFoundException("Could not find tutorial with title: " + title);
		}

		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	/***********************************/

	/**
	 * Joining two tables
	 */

	@GetMapping("/authors/{authorId}/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorialsByAuthorId(
							@PathVariable(value = "authorId") Long authorId){
		return new ResponseEntity<>(tutorialRepository.findByAuthorId(authorId), HttpStatus.OK);
	}

	@PostMapping("/authors/{authorId}/tutorials")
	public ResponseEntity<Tutorial> createTutorial(
						@PathVariable(value = "authorId") Long authorId,
						@RequestBody Tutorial tutorial
	){
		Optional<Author> author = authorRepository.findById(authorId);
		tutorial.setAuthor(author.get());

		return new ResponseEntity<>(tutorialRepository.save(tutorial), HttpStatus.CREATED);
	}

	/***********************************/

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
			List<Tutorial> tutorials = new ArrayList<>();

			System.out.println(title);

			if (title == null){
				tutorialRepository.findAll().forEach(tutorials::add);
			}
			else
				tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

			if (tutorials.isEmpty()) {
				throw new TutorialNotFoundException("Could not find tutorial with title: " + title);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		try {
			Tutorial _tutorial = tutorialRepository
					.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

		if (tutorialData.isPresent()) {
			Tutorial _tutorial = tutorialData.get();
			_tutorial.setTitle(tutorial.getTitle());
			_tutorial.setDescription(tutorial.getDescription());
			_tutorial.setPublished(tutorial.isPublished());
			return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
		} else {
			throw new TutorialNotFoundException("Could not find tutorial with id: " + id);
		}
	}

	/**
	 * PATCH only title
	 **/
	@PatchMapping("/tutorials/{id}/{title}")
	public ResponseEntity<Tutorial> patchTutorialByTitle(@PathVariable("id") long id,
														 @PathVariable("title") String title){
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
		if(!tutorialData.isPresent())
			throw new TutorialNotFoundException("Could not find tutorial with id: " + id);

		Tutorial tutorialDto = tutorialData.get();
		tutorialDto.setTitle(title);

		return new ResponseEntity<>(tutorialRepository.save(tutorialDto), HttpStatus.OK);
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished() {
		try {
			List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
