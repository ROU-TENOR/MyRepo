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
import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class utilisateurServiceTest {

	
	@Autowired
	UtilisateurService usrServ;
	@Autowired
	DepartementService departementService;
	private Utilisateur usr = new Utilisateur();
	private Departement departement = new Departement();
	@Before
	public void init()
	{
		departement.setLibelle("DSI");
		departement=departementService.addDepartement(departement);
		usr.setFirstName("ahmed");
		usr.setSecondName("ouahmane");
		usr.setDepartement(departement);
		usr=usrServ.saveUtilisateur(usr);
		usr.setFirstName("khalid");
		usr.setSecondName("ouahmane");
		usr.setDepartement(departement);
		usr=usrServ.saveUtilisateur(usr);
		
	}
	
	@Test
	public void createTest()  {
		log.info("depppp"+departement);
		usr.setFirstName("rachid");
		usr.setSecondName("ouahmane");
		usr.setDepartement(departement);
		usr=usrServ.saveUtilisateur(usr);
		assertEquals("rachid", usr.getFirstName());
	}
	@Test(expected = RequiredFieldException.class)
	public void createTestRequiredFieldException()  {
		log.info("depppp"+departement);
		usr.setFirstName("");
		usr.setSecondName("ouahmane");
		usr.setDepartement(departement);
		usr=usrServ.saveUtilisateur(usr);
		assertEquals("rachid", usr.getFirstName());
	}



	@Test
	public void deleteTest() {
		
		usrServ.deleteUtilisateur(usr);
		 Optional<Utilisateur> user=usrServ.getById(usr.getId());
		 Boolean foundBoolean=user.isPresent();
		 assertFalse(foundBoolean);
	}

	@Test(expected = NullPointerException.class)
	public void deleteNullException() {
		Utilisateur user=null;
		usrServ.deleteUtilisateur(user);
	}
	@Test(expected = NotFoundException.class)
	public void deleteNotFoundException() {
		Utilisateur user=new Utilisateur();
		user.setId(102L);
		usrServ.deleteUtilisateur(user);
	}

	@Test
	public void findByIdTest() {
		Optional<Utilisateur> user = usrServ.getById(usr.getId());
		 Boolean foundBoolean=user.isPresent();
		 assertTrue(foundBoolean);

	}

	@Test
	public void findByIdNotPresntTest() {
		Optional<Utilisateur> user = usrServ.getById(1021l);
		Boolean foundBoolean=user.isPresent();
		assertFalse(foundBoolean);
	}


	@Test
	public void updateTest() {
		
		usr.setFirstName("Mohamed");
		usr=usrServ.saveUtilisateur(usr);
		String libelleString="Mohamed";
		assertEquals(libelleString,usr.getFirstName());
	}

}
