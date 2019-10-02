package com.tenor.tsf.gs.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;
import com.tenor.tsf.gs.repository.UtilisateurRepository;

@Service
public class UtilisateurService{

	@Autowired
	private UtilisateurRepository<Utilisateur> utilisateurRepository;
//	@Autowired
//	private PasswordEncoder bcryptEncoder;

//	public UserDetails loadUserByUsername(String username) {
//		Utilisateur user = utilisateurRepository.findByUsername(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//		return new org.springframework.security.core.userdetails.User(user.getPseudo(), user.getPassword(),
//				new ArrayList<>());
//	}
//	
	

	@Transactional
	public List<Utilisateur> getAllUtilisateurs() {
		return (List<Utilisateur>) utilisateurRepository.findAll();
	}

	@Transactional
	public Optional<Utilisateur> getById(Long id) {

		return utilisateurRepository.findById(id);
	}

	@Transactional
	public void deleteUtilisateur(Utilisateur utilisateurId) {

		Validate.notNull(utilisateurId, "object given is null");
		Optional<Utilisateur> mUser = getById(utilisateurId.getId());
		if (!mUser.isPresent())
			throw new NotFoundException("user " + utilisateurId.getId() + "not found");

		utilisateurRepository.delete(utilisateurId);
	}

	@Transactional
	public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
		Validate.notNull(utilisateur, "object given is null");
		if (utilisateur.getFirstName() == "")
			throw new RequiredFieldException("user first name is empty");
		if (utilisateur.getSecondName() == "")
			throw new RequiredFieldException("user secound name is empty");
		if (utilisateur.getDepartement() == null)
			throw new RequiredFieldException("user departement name is empty");
	//	utilisateur.setPassword(bcryptEncoder.encode(utilisateur.getPassword()));
		return utilisateurRepository.save(utilisateur);
	}

	@Transactional
	public Utilisateur updateUtilisateur(Utilisateur Utilisateur) {
		return utilisateurRepository.save(Utilisateur);
	}

}
