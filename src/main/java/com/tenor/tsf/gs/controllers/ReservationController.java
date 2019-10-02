package com.tenor.tsf.gs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenor.tsf.gs.entity.Reservation;
import com.tenor.tsf.gs.exceptions.NotFoundException;
import com.tenor.tsf.gs.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	

	

	@GetMapping
	public  List<Reservation> getAll() {
		List<Reservation> listReservations = reservationService.getAllReservations();
		if (listReservations.isEmpty()) {
			throw new NotFoundException("list is empty");
		}
		return listReservations;

	}

	@GetMapping("/{id}")
	public  Reservation getById(@PathVariable Long id) {
		
		Reservation reservation = reservationService.getById(id);
		return reservation;

	}

	@PostMapping
	public ResponseEntity<Reservation> saveDepart(@RequestBody Reservation reservation) {
		Reservation reserv=reservationService.addReservation(reservation);
		return new ResponseEntity<Reservation>(reserv,HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<String> deleteDep(@RequestBody Reservation reservation) {
		reservationService.deleteReservation(reservation);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	
}
