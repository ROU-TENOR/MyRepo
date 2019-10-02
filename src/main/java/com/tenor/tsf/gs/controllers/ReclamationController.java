package com.tenor.tsf.gs.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenor.tsf.gs.entity.Reclamation;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.service.ReclamationService;
@RestController
@RequestMapping(value = "/reclamations")
public class ReclamationController {
	@Autowired
	ReclamationService reclamationService;
	

	

	@GetMapping
	public @ResponseBody List<Reclamation> getAll() {
		List<Reclamation> listReclamations = reclamationService.getAllReclamations();
		if (listReclamations.isEmpty()) {
			throw new NotFoundException("list is empty");
		}
		return listReclamations;

	}

	@GetMapping("/{id}")
	public @ResponseBody Reclamation getById(@PathVariable Long id) {
		
		Optional<Reclamation> reclamation = reclamationService.getById(id);
		if (!reclamation.isPresent()) {
			throw new NotFoundException("reclamation wanted is not found");
		}
		return reclamation.get();

	}

	@PostMapping
	public ResponseEntity<String> saveDepart(@RequestBody Reclamation reclamation) {
		
		reclamationService.saveReclamation(reclamation);
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping
	public ResponseEntity<String> deleteDep(@RequestBody Reclamation reclamation) {
		reclamationService.deleteReclamation(reclamation);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
