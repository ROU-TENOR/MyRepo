package com.tenor.tsf.gs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tenor.tsf.gs.entity.Departement;
import com.tenor.tsf.gs.repository.DepartementRepository;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class GestionSalleApplicationTests {

	@Autowired
	DepartementRepository depRep;

	@Test
	public void contextLoads() {
		Departement departement = new Departement();
		log.info(departement);
		departement.setLibelle("DSI");
		departement=depRep.save(departement);
		//Optional<Departement>ndepartement=depRep.findById(departement.getId());
		log.info(departement);
	}

}
