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

import com.tenor.tsf.gs.entity.Materiel;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.service.MaterielService;
@RestController
@RequestMapping(value = "/materiels")
public class MaterielController {
	@Autowired
	MaterielService materielService;
	

	

	@GetMapping
	public @ResponseBody List<Materiel> getAll() {
		List<Materiel> listMateriels = materielService.findAll();
		if (listMateriels.isEmpty()) {
			throw new NotFoundException("list is empty");
		}
		return listMateriels;

	}

	@GetMapping("/{id}")
	public @ResponseBody Materiel getById(@PathVariable Long id) {
		
		Optional<Materiel> materiel = materielService.findById(id);
		if (!materiel.isPresent()) {
			throw new NotFoundException("Materiel wanted is not found");
		}
		return materiel.get();

	}

	@PostMapping
	public ResponseEntity<Materiel> saveDepart(@RequestBody Materiel materiel) {
		
		materiel=materielService.saveMateriel(materiel);
		return new ResponseEntity<Materiel>(materiel,HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<String> deleteDep(@RequestBody Materiel materiel) {
		materielService.delete(materiel);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
