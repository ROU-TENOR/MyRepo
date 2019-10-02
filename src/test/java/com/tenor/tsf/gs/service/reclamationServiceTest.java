package com.tenor.tsf.gs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tenor.tsf.gs.entity.Departement;
import com.tenor.tsf.gs.entity.Materiel;
import com.tenor.tsf.gs.entity.Reclamation;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class reclamationServiceTest {

	
	@Autowired
	ReclamationService recServ;
	@Autowired
	SalleService salleServ;
	@Autowired
	UtilisateurService userServ;
	@Autowired
	DepartementService depServ;
	
	
	
	
	Reclamation reclamation=new Reclamation();
	Salle salle=new Salle();
	private Departement departement = new Departement();

	Utilisateur user=new Utilisateur();
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
		
		reclamation.setSalle(salle);
		reclamation.setUser(user);
		reclamation.setMessage("Salle 1 clim en pane ");
		reclamation=recServ.saveReclamation(reclamation);
	}

	@Test
	public void createTest() {
		reclamation.setSalle(salle);
		reclamation.setUser(user);
		reclamation.setMessage("Salle 2 clim en pane");
		reclamation=recServ.saveReclamation(reclamation);
		
		assertEquals("Salle 2 clim en pane", reclamation.getMessage());
	}
	
	@Test (expected = RequiredFieldException.class)
	public void testCreate() {
		reclamation.setSalle(salle);
		reclamation.setUser(user);
		reclamation.setMessage("");
		reclamation=recServ.saveReclamation(reclamation);
		
		//assertEquals("Salle 2 clim en pane", reclamation.getMessage());
		
	}

	@Test(expected = NullPointerException.class)
	public void createKoTest() {
		reclamation = null;
		recServ.saveReclamation(reclamation);
	}

	@Test
	public void deleteTest() {
		
		recServ.deleteReclamation(reclamation);
		 Optional<Reclamation> materiel=recServ.getById(reclamation.getId());
		 Boolean foundBoolean=materiel.isPresent();
		 assertFalse(foundBoolean);
	}

	@Test(expected = NullPointerException.class)
	public void deleteNullException() {
		Reclamation myReclamation=null;
		recServ.deleteReclamation(myReclamation);
	}
	@Test(expected = NotFoundException.class)
	public void deleteNotFoundException() {
		Reclamation myReclamation=new Reclamation();
		myReclamation.setId(102L);
		recServ.deleteReclamation(myReclamation);
	}

	@Test
	public void findByIdTest() {
		Optional<Reclamation> myReclamation = recServ.getById(reclamation.getId());
		 Boolean foundBoolean=myReclamation.isPresent();
		 assertTrue(foundBoolean);

	}

	@Test
	public void findByIdNotPresntTest() {
		Optional<Reclamation> myReclamation = recServ.getById(1021l);
		Boolean foundBoolean=myReclamation.isPresent();
		assertFalse(foundBoolean);
	}


	@Test
	public void updateTest() {
		
		reclamation.setMessage("salle de réunion clim en pane");
		reclamation=recServ.saveReclamation(reclamation);
		String libelleString="salle de réunion clim en pane";
		assertEquals(libelleString,reclamation.getMessage());
	}


}
