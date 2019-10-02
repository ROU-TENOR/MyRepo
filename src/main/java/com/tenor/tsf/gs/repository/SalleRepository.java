package com.tenor.tsf.gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tenor.tsf.gs.entity.Salle;

public interface SalleRepository<P> extends CrudRepository<Salle, Long>{

@Query(value = "select * from salle s "
		+ "where s.id  not in "
		+ "(select reservation.salle_id "
		+ "from reservation where reservation.date_debut BETWEEN ?1 and ?2 "
		+ "and date_fin BETWEEN ?1 and ?2)",nativeQuery = true)
	public List<Salle> availableSalle( String dateD,  String dateF);
	

@Query(value = "select * from salle s "
		+ "where s.id  ="
		+ " (select reservation.salle_id from reservation "
		+ "where (reservation.date_debut BETWEEN ?2 and ?3 or date_fin BETWEEN ?2 and ?3 )"
		+ " and reservation.salle_id=?1)",nativeQuery = true)
public Salle checkSalle(String salleId, String dateD,  String dateF);

}
