package com.tenor.tsf.gs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tenor.tsf.gs.entity.Materiel;
@Repository
public interface MaterielRepository extends CrudRepository<Materiel, Long> {

}
