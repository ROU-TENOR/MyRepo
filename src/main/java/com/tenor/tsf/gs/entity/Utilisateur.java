package com.tenor.tsf.gs.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Utilisateur implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6088509751056130555L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String secondName;
	private String function;
	private String pseudo;
	private String password;
	@ManyToOne
	private Departement departement;
	
	
	
}
