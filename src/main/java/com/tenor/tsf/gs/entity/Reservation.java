package com.tenor.tsf.gs.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class Reservation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517394340537366432L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	@ManyToOne
	private Utilisateur user; 
	@ManyToOne
	private Salle salle;
	 

	
}
