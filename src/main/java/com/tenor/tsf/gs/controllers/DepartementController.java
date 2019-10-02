package com.tenor.tsf.gs.controllers;

import java.util.List;
import java.util.NoSuchElementException;

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
import org.springframework.web.server.ResponseStatusException;

import com.tenor.tsf.gs.entity.Departement;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.service.DepartementService;

@RequestMapping("/departements")
@RestController
public class DepartementController {
	@Autowired
	DepartementService depDervice;

	@GetMapping
	public @ResponseBody List<Departement> getAll() {
		List<Departement> listDepartements = depDervice.getAllDepartements();
		if (listDepartements.isEmpty()) {
			throw new NotFoundException("msg");
		}
		return listDepartements;

	}

	@GetMapping("/{id}")
	public @ResponseBody Departement getById(@PathVariable Long id) {
		Departement departement = null;
		try {
			departement = depDervice.getById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return departement;

	}

	@PostMapping
	public ResponseEntity<Departement> saveDepart(@RequestBody Departement departement) {

		departement=depDervice.addDepartement(departement);

		return new ResponseEntity<Departement>(departement, HttpStatus.OK);

	}

	@DeleteMapping
	public void deleteDep(@RequestBody Departement departement) {
		depDervice.deleteDepartement(departement);
		 //ResponseEntity.status(HttpStatus.OK).build();
	}

}
