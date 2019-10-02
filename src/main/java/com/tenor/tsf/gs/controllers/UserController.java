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

import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.service.UtilisateurService;
@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	UtilisateurService userService;

	@GetMapping
	public @ResponseBody List<Utilisateur> getAll() {
		List<Utilisateur> listUtilisateurs = userService.getAllUtilisateurs();
		if (listUtilisateurs.isEmpty()) {
			throw new NotFoundException("list is empty");
		}
		return listUtilisateurs;

	}

	@GetMapping("/{id}")
	public @ResponseBody Utilisateur getById(@PathVariable Long id) {

		Optional<Utilisateur> user = userService.getById(id);;
		if (user.isPresent()) {
			throw new NotFoundException("user given is not found");
		}
		return user.get();

	}

	@PostMapping()
	public ResponseEntity<String> saveDepart(@RequestBody Utilisateur user) {

		userService.saveUtilisateur(user);
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping
	public ResponseEntity<String> deleteDep(@RequestBody Utilisateur user) {
		userService.deleteUtilisateur(user);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
