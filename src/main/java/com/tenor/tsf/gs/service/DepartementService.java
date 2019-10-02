package com.tenor.tsf.gs.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenor.tsf.gs.entity.Departement;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;
import com.tenor.tsf.gs.repository.DepartementRepository;
@Service
public class DepartementService {

	@Autowired
	private DepartementRepository departementRepository;

	
	public List<Departement> getAllDepartements() {
		return (List<Departement>) departementRepository.findAll();
	}

	
	public Departement getById(Long id) {
		// id must not be null
		try {
			return departementRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException(e.getMessage());
		}
		
	}

	@Transactional
	public void deleteDepartement(Departement departement) {
		// return illegale exception if the given
		Departement checkDepartement = getById(departement.getId());
		
		departementRepository.delete(checkDepartement);
	}

	@Transactional
	public Departement addDepartement(Departement departement) {
		// id is auto increment
		//
		// LOGGER.info(dept);
		if (departement.getLibelle() == "") {
			throw new RequiredFieldException("departement name is empty");
		}

		return departementRepository.save(departement) ;
	}

	

}
