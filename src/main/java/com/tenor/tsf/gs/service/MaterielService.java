package com.tenor.tsf.gs.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenor.tsf.gs.entity.Materiel;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;
import com.tenor.tsf.gs.repository.MaterielRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MaterielService {

	@Autowired
	private MaterielRepository materielRepository;
	@Autowired
	SalleService salleService;

	public Materiel saveMateriel(Materiel mat) {
		log.info(mat);

		Validate.notNull(mat);

		if (mat.getLibelle() == "") {
			throw new RequiredFieldException("Mat libelle is empty");
		}
		if (mat.getSalle() == null) {
			throw new RequiredFieldException("Mat libelle is empty");
		}
		
		log.info(mat);
		return materielRepository.save(mat);

	}

//	public void update(Materiel materiel) {
//		Validate.notNull(materiel, "object given is null");
//		Materiel mat = findById(materiel.getId());
//		log.info(mat);
//
//		if (mat == null) {
//			throw new NotFoundException("materiel " + materiel.getId() + " not found");
//		}
//		int index = MaterielDB.materiels.indexOf(mat);
//		MaterielDB.materiels.set(index, materiel);
//		mat = findById(materiel.getId());
//		log.info(mat);
//
//	}

	public void delete(Materiel materiel) {
		Validate.notNull(materiel, "Object given is null");
		log.info(materiel);
		Optional<Materiel> mat = findById(materiel.getId());
		
		log.info(mat);

		log.info(materiel);
		if (!mat.isPresent()) {
			throw new NotFoundException("materiel " + materiel.getId() + " not found");
		}
		materielRepository.delete(materiel);

	}

	public Optional<Materiel> findById(Long id) {
		Validate.notNull(id, "id given must not be null ");
		// log.info( materiel);
		return materielRepository.findById(id);

	}

	public List<Materiel> findAll() {
		// log.info( materiel);
		return (List<Materiel>) materielRepository.findAll();

	}

}
