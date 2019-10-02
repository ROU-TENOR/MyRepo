package com.tenor.tsf.gs.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tenor.tsf.gs.entity.Departement;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class DepartementServiceTest {

	
	@Autowired
	DepartementService departementService;
	Departement departement=new Departement();

	@Before
	public void init()
	{
		departement.setLibelle("DSI 1");
		log.info(departement);
		 departement=departementService.addDepartement(departement);
		//Optional<Departement> ndepartement=departementService.getById(departement.getId());
		//log.info(ndepartement);
		log.info(departement);
		
	}
	
	@Test
	public void testgetall() {
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+departementService.getAllDepartements());
		
	}
	
	@Test
	public void testAddDepartement() {
		departement.setLibelle("DSI 3");
		log.info(departement);
		 departement=departementService.addDepartement(departement);
		log.info(departement);
		assertEquals("DSI 3", departement.getLibelle());
		
		
		
	}
	@Test
	public void testgetAllDepartement() {
		//if the object is given is null will throw nullpointerexception
		//save do not care if id is null if null will add new one if not it will alter it
		
		List<Departement> ndepartement=departementService.getAllDepartements();
		log.info("list depts"+ndepartement);

		
		
		
	}
	@Test
	public void testdelete() {
		// the object given as parameter must not be null also as the id of object
		Departement departement=new Departement();
		departement.setId(111l);
		departement.setLibelle("raa");
		departement=departementService.addDepartement(departement);
		log.info(departement);
		departementService.deleteDepartement(departement);
		List<Departement> ndepartement=departementService.getAllDepartements();
		log.info("list depts"+ndepartement);
		
	}
}
