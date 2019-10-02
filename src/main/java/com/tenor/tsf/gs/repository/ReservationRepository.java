package com.tenor.tsf.gs.repository;

import org.springframework.data.repository.CrudRepository;

import com.tenor.tsf.gs.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	
}
