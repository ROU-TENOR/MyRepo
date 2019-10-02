package com.tenor.tsf.gs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tenor.tsf.gs.entity.Departement;

@Repository
public interface DepartementRepository extends CrudRepository<Departement,Long> {

}
