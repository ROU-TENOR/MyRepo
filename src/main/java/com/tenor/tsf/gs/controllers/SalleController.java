package com.tenor.tsf.gs.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.service.SalleService;
@RestController
@RequestMapping("/salles")
public class SalleController {
	@Autowired
	SalleService salleService;

	@GetMapping
	public @ResponseBody List<Salle> getAll() {
		List<Salle> listSalles = salleService.getAllSalles();
		if (listSalles.isEmpty()) {
			throw new NotFoundException("list is empty");
		}
		return listSalles;

	}
	@GetMapping("/availableSalles")
	public @ResponseBody List<Salle> availableSalles(@RequestParam LocalDate dateD,@RequestParam LocalDate dateF) {
		List<Salle> listSalles = salleService.availableSalle(dateD, dateF);
		if (listSalles.isEmpty()) {
			throw new NotFoundException("list is empty");
		}
		return listSalles;
		
	}

	@GetMapping("/{id}")
	public @ResponseBody Salle getById(@PathVariable Long id) {

		Optional<Salle> salle = salleService.getById(id);;
		if (salle.isPresent()) {
			throw new NotFoundException("salle given is not found");
		}
		return salle.get();

	}

	@PostMapping
	public ResponseEntity<String> saveSalle(@RequestBody Salle salle) {

		salle=salleService.addSalle(salle);
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping
	public ResponseEntity<String> deleteSalle(@RequestBody Salle salle) {
		salleService.deleteSalle(salle);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
