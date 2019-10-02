package com.tenor.tsf.gs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tenor.tsf.gs.entity.Departement;
import com.tenor.tsf.gs.entity.Reservation;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.exceptions.AllreadyReservedException;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class ReservationServiceTest {


	@Autowired
	ReservationService resServ;
	@Autowired
	SalleService salleServ;
	@Autowired
	UtilisateurService userServ;
	@Autowired
	DepartementService depServ;
	
	
	
	
	Reservation reservation=new Reservation();
	private Salle salle=new Salle();
	private Departement departement = new Departement();
	private Utilisateur user=new Utilisateur();
	private LocalDate dateDebut=LocalDate.of(2021, 9, 11);
	private LocalDate dateFin=LocalDate.of(2021, 9, 15);
	
	@Before
	 public void init() {
		salle.setLibelle("DSI");
		salle=salleServ.addSalle(salle);
		
		departement.setLibelle("DSI");
		departement=depServ.addDepartement(departement);
		user.setFirstName("ahmed");
		user.setSecondName("ouahmane");
		user.setDepartement(departement);
		user=userServ.saveUtilisateur(user);
		
		reservation.setSalle(salle);
		reservation.setUser(user);
		reservation.setDateDebut(dateDebut);;
		reservation.setDateFin(dateFin);
		reservation=resServ.addReservation(reservation);
	}
	@After
	public void destroy() {
		resServ.deleteAll();
	}

	@Test
	public void createTest() {
		dateDebut=LocalDate.of(2021, 9, 22);
		dateFin=LocalDate.of(2021, 9, 24);
		reservation.setSalle(salle);
		reservation.setUser(user);
		reservation.setDateDebut(dateDebut);;
		reservation.setDateFin(dateFin);
		reservation=resServ.addReservation(reservation);
		
		assertEquals(dateDebut, reservation.getDateDebut());
	}
	@Test(expected = AllreadyReservedException.class)
	public void createTestAlreadyReservedEx() {
		dateDebut=LocalDate.of(2021, 9, 11);
		dateFin=LocalDate.of(2021, 9, 17);
		reservation.setSalle(salle);
		reservation.setUser(user);
		reservation.setDateDebut(dateDebut);;
		reservation.setDateFin(dateFin);
		reservation=resServ.addReservation(reservation);
		
		//assertEquals(dateDebut, reservation.getDateDebut());
	}
	
//	@Test (expected = RequiredFieldException.class)
//	public void testCreate() {
//		reservation.setSalle(salle);
//		reservation.setUser(user);
//		reservation.setMessage("");
//		reservation=resServ.savereservation(reservation);
//		
//		//assertEquals("Salle 2 clim en pane", reservation.getMessage());
//		
//	}
//
//	@Test(expected = NullPointerException.class)
//	public void createKoTest() {
//		reservation = null;
//		resServ.savereservation(reservation);
//	}
//
//	@Test
//	public void deleteTest() {
//		
//		resServ.deletereservation(reservation);
//		 Optional<reservation> materiel=resServ.getById(reservation.getId());
//		 Boolean foundBoolean=materiel.isPresent();
//		 assertFalse(foundBoolean);
//	}
//
//	@Test(expected = NullPointerException.class)
//	public void deleteNullException() {
//		reservation myreservation=null;
//		resServ.deletereservation(myreservation);
//	}
//	@Test(expected = NotFoundException.class)
//	public void deleteNotFoundException() {
//		reservation myreservation=new reservation();
//		myreservation.setId(102L);
//		resServ.deletereservation(myreservation);
//	}
//
//	@Test
//	public void findByIdTest() {
//		Optional<reservation> myreservation = resServ.getById(reservation.getId());
//		 Boolean foundBoolean=myreservation.isPresent();
//		 assertTrue(foundBoolean);
//
//	}
//
//	@Test
//	public void findByIdNotPresntTest() {
//		Optional<reservation> myreservation = resServ.getById(1021l);
//		Boolean foundBoolean=myreservation.isPresent();
//		assertFalse(foundBoolean);
//	}
//
//
//	@Test
//	public void updateTest() {
//		
//		reservation.setMessage("salle de réunion clim en pane");
//		reservation=resServ.savereservation(reservation);
//		String libelleString="salle de réunion clim en pane";
//		assertEquals(libelleString,reservation.getMessage());
//	}

}
