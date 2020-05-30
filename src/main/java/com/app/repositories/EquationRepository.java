package com.app.repositories;


import com.app.models.Equation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;


@Repository
public interface EquationRepository extends CrudRepository<Equation, Integer> {
    Iterable<Equation> findAll();
    Equation findByFirstSloganAndSumAndMinAndMax(Double first_slogan, Double sum, Double min, Double max);
}
