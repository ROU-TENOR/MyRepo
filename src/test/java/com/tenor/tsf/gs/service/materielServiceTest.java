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

import com.tenor.tsf.gs.entity.Materiel;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class materielServiceTest {
	
	@Autowired
	SalleService salleServ;
	Salle salle=new Salle();
	@Autowired
	
	private MaterielService matServ;
	private Materiel mat = new Materiel();

	@Before
	 public void init() {
		salle.setLibelle("DSI");
		salle=salleServ.addSalle(salle);
		mat.setLibelle("HP");
		mat.setSalle(salle);
		log.info(mat);
		mat=matServ.saveMateriel(mat);
		log.info(mat);
		mat.setLibelle("Dell");
		mat.setSalle(salle);
		log.info(mat);
		mat=matServ.saveMateriel(mat);
		log.info(mat);
		
	}
	@Test
	public void createTest() {
		mat.setLibelle("lblcreate4");
		mat.setSalle(salle);
		mat = matServ.saveMateriel(mat);
		
		assertEquals("lblcreate4", mat.getLibelle());
	}
	@Test (expected = RequiredFieldException.class)
	public void testCreate() {
		mat.setLibelle("lblcreate4");
		mat.setSalle(null);
		mat = matServ.saveMateriel(mat);
		
	}

	@Test(expected = NullPointerException.class)
	public void createKoTest() {
		mat = null;
		matServ.saveMateriel(mat);
	}

	@Test
	public void deleteTest() {
		
		matServ.delete(mat);
		 Optional<Materiel> materiel=matServ.findById(mat.getId());
		 Boolean foundBoolean=materiel.isPresent();
		 assertFalse(foundBoolean);
	}

	@Test(expected = NullPointerException.class)
	public void deleteNullException() {
		Materiel materiel=null;
		matServ.delete(materiel);
	}
	@Test(expected = NotFoundException.class)
	public void deleteNotFoundException() {
		Materiel materiel=new Materiel();
		materiel.setId(102L);
		matServ.delete(materiel);
	}

	@Test
	public void findByIdTest() {
		Optional<Materiel> materiel = matServ.findById(mat.getId());
		 Boolean foundBoolean=materiel.isPresent();
		 assertTrue(foundBoolean);

	}

	@Test
	public void findByIdNotPresntTest() {
		Optional<Materiel> materiel = matServ.findById(1021l);
		Boolean foundBoolean=materiel.isPresent();
		assertFalse(foundBoolean);
	}


	@Test
	public void updateTest() {
		mat.setId(mat.getId());
		mat.setLibelle("salle de réunion");
		mat=matServ.saveMateriel(mat);
		String libelleString="salle de réunion";
		assertEquals(libelleString,mat.getLibelle());
	}



}
