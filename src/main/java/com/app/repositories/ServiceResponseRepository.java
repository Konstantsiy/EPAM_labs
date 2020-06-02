package com.app.repositories;

import com.app.models.ServiceResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;



@Repository
public interface ServiceResponseRepository extends CrudRepository<ServiceResponse, Long> {
    Iterable<ServiceResponse> findAll();

    Iterable<ServiceResponse> findAllById(Integer id);

    ServiceResponse findById(Integer id);
}
