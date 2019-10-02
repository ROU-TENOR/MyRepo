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

import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class salleServiceTest {

	
	@Autowired
	SalleService salleServ;
	Salle salle=new Salle();
	
	@Before
	 public void init() {
		salle.setLibelle("DSI");
		salle=salleServ.addSalle(salle);
		log.info(salle);
		
	}

	@Test
	public void createTest() {
		salle.setLibelle("Salle 1");
		salle = salleServ.addSalle(salle);
		assertEquals("Salle 1", salle.getLibelle());
	}
	@Test (expected = RequiredFieldException.class)
	public void testCreate() {
		salle.setLibelle("");
		salle = salleServ.addSalle(salle);
		
	}

	@Test(expected = NullPointerException.class)
	public void createKoTest() {
		salle = null;
		salleServ.addSalle(salle);
	}

	@Test
	public void deleteTest() {
		
		salleServ.deleteSalle(salle);
		 Optional<Salle> salleeriel=salleServ.getById(salle.getId());
		 Boolean foundBoolean=salleeriel.isPresent();
		 assertFalse(foundBoolean);
	}

	@Test(expected = NullPointerException.class)
	public void deleteNullException() {
		Salle sal=null;
		salleServ.deleteSalle(sal);
	}
	@Test(expected = NotFoundException.class)
	public void deleteNotFoundException() {
		Salle mySalle=new Salle();
		mySalle.setId(102L);
		salleServ.deleteSalle(mySalle);
	}

	@Test
	public void findByIdTest() {
		Optional<Salle> mySalle = salleServ.getById(salle.getId());
		 Boolean foundBoolean=mySalle.isPresent();
		 assertTrue(foundBoolean);

	}

	@Test
	public void findByIdNotPresntTest() {
		Optional<Salle> mySalle = salleServ.getById(1021l);
		Boolean foundBoolean=mySalle.isPresent();
		assertFalse(foundBoolean);
	}


	@Test
	public void updateTest() {
		
		salle.setLibelle("salle de réunion");
		salle=salleServ.addSalle(salle);
		String libelleString="salle de réunion";
		assertEquals(libelleString,salle.getLibelle());
	}



}
