package com.tenor.tsf.gs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenor.tsf.gs.entity.Reservation;
import com.tenor.tsf.gs.exceptions.AllreadyReservedException;
import com.tenor.tsf.gs.exceptions.DateExpection;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.exceptions.RequiredFieldException;
import com.tenor.tsf.gs.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private SalleService salleService;
	

	@Transactional
	public List<Reservation> getAllReservations() {
		return (List<Reservation>) reservationRepository.findAll();
	}

	@Transactional
	public Reservation getById(Long id) {
		Optional<Reservation> optional = reservationRepository.findById(id);

		if (!optional.isPresent()) {
			throw new NotFoundException("Reservation with id" + id + " wanted not found ");
		}
		return optional.get();
	}

	@Transactional
	public void deleteReservation(Reservation ReservationId) {
		Validate.notNull(ReservationId, "object given is null");
		Reservation reservation = getById(ReservationId.getId());
		reservationRepository.delete(reservation);
	}

	@Transactional
	public Reservation addReservation(Reservation reservation) {
		Validate.notNull(reservation, "object given is null");
		if (reservation.getSalle() == null)
			throw new RequiredFieldException("salle given is null");
		if (reservation.getUser() == null)
			throw new RequiredFieldException("usr given is null");
		if (reservation.getDateDebut() == null)
			throw new RequiredFieldException("debut date is null ");
		if (reservation.getDateFin() == null)
			throw new RequiredFieldException("fin date is null");
		if (reservation.getDateDebut().isBefore(LocalDate.now()) || reservation.getDateFin().isBefore(LocalDate.now()))
			throw new DateExpection("one of dates are before current date");
		if (reservation.getDateFin().isBefore(reservation.getDateDebut()))
			throw new DateExpection("fin date cant be before debt date");
		

		if (salleService.checkSalle(reservation.getSalle(), reservation.getDateDebut(),
				reservation.getDateFin()) != null) {
			throw new AllreadyReservedException("this reservation wanted is unavailable");
			}
		return reservationRepository.save(reservation);
	}

	@Transactional
	public Reservation updateReservation(Reservation reservation) {
		Validate.notNull(reservation, "object given is null");
		return reservationRepository.save(reservation);
	}

	@Transactional
	public void deleteAll() {
		reservationRepository.deleteAll();
	}

}
