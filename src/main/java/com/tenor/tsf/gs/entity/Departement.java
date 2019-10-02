package com.tenor.tsf.gs.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

@Data
@Entity
public class Departement implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3535874673462105413L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String libelle;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	private List<Utilisateur> users;


}
