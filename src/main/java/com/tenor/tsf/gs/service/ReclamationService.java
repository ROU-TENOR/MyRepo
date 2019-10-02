package com.tenor.tsf.gs.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenor.tsf.gs.entity.Reclamation;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.repository.ReclamationRepository;
@Service
public class ReclamationService {

	@Autowired
	private ReclamationRepository<Reclamation> reclamationRepository;
	
	@Transactional
	public List<Reclamation> getAllReclamations() {
		return (List<Reclamation>) reclamationRepository.findAll();
	}

	@Transactional
	public Optional<Reclamation> getById(Long id) {
		Validate.notNull(id, "id given must not be null");
		return reclamationRepository.findById(id);
	}

	@Transactional
	public void deleteReclamation(Reclamation reclamationId) {
		Validate.notNull(reclamationId, "object given is null");
		Optional<Reclamation> reclamation = getById(reclamationId.getId());
		if (!reclamation.isPresent())
			throw new NotFoundException("reclamation " + reclamationId.getId() + " not found");
		reclamationRepository.delete(reclamationId);
	}

	@Transactional
	public Reclamation saveReclamation(Reclamation reclamation) {
		if (reclamation.getMessage() == "")
			throw new RequiredFieldException("reclamation msg empty ");
		if (reclamation.getSalle() == null)
			throw new RequiredFieldException("reclamation salle empty");
		if (reclamation.getUser() == null)
			throw new RequiredFieldException("reclamation user empty");
		
		return reclamationRepository.save(reclamation) ;
	}

	/*
	 * @Transactional public boolean updateReclamation(Reclamation reclamation) {
	 * return reclamationRepository.save(reclamation) != null; }
	 */
}
