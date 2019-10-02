package com.tenor.tsf.gs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tenor.tsf.gs.entity.Utilisateur;

public interface UtilisateurRepository<P> extends CrudRepository<Utilisateur, Long>{
	
	@Query("select u from Utilisateur u where u.pseudo=?1")
	public Utilisateur findByUsername(String username);
	
}
