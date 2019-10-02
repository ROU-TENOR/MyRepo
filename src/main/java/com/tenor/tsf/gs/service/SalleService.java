package com.tenor.tsf.gs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;
import com.tenor.tsf.gs.repository.SalleRepository;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class SalleService {
	
	@Autowired
	private SalleRepository<Salle> salleRepository;
	



	@Transactional
	public List<Salle> getAllSalles() {
		List<Salle> list= (List<Salle>) salleRepository.findAll();
		log.info(list);
		return list;
	}
	@Transactional
	public List<Salle> availableSalle(LocalDate dateD,LocalDate dateF) {
		log.info(dateD);
		Validate.notNull(dateD);
		Validate.notNull(dateF);
		List<Salle> list= (List<Salle>) salleRepository.availableSalle(dateD.toString(), dateF.toString());
		log.info(list);
		return list;
	}

	

	@Transactional
	public Optional<Salle> getById(Long id) {
		log.info(id);
		Validate.notNull(id);
		Optional<Salle> salleOptional=salleRepository.findById(id);
		log.info(salleOptional);
		return salleRepository.findById(id);
	}

	@Transactional
	public void deleteSalle(Salle salleId) {
		log.info(salleId);
		Optional<Salle> sal = this.getById(salleId.getId());
		log.info(sal);
		//check if in database
		if (!sal.isPresent()) {
			throw new NotFoundException("salle "+salleId.getId()+"not found");
		}
		salleRepository.delete(salleId);

	}

	@Transactional
	public Salle addSalle(Salle salle) {
		log.info(salle);
		Validate.notNull(salle);
		//required field
		if (salle.getLibelle() == "") {
			throw new RequiredFieldException("salle name is empty");
		}
		return salleRepository.save(salle);
	}

	@Transactional
	public boolean updateSalle(Salle salle) {
		return salleRepository.save(salle) != null;
	}
	public Salle checkSalle(Salle salle, LocalDate dateDebut, LocalDate dateFin) {
		log.info(salle+" "+dateDebut+" "+dateFin);
		Validate.notNull(salle, "salle object given is null");
		Validate.notNull(dateDebut, "dateDebut object given is null");
		Validate.notNull(dateDebut, "dateFin object given is null");
		return salleRepository.checkSalle(salle.getId().toString(), dateDebut.toString(), dateFin.toString());

	}
}
