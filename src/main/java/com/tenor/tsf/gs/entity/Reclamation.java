package com.tenor.tsf.gs.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
@Entity
@Data
public class Reclamation implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5296020112422361013L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private LocalDate dateRec;
	
	@ManyToOne
	private Utilisateur user;
	@ManyToOne
	private Salle salle;

	private Integer Statu;
	private String message;
	
	
	
	
}
