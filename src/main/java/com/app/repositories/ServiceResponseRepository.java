package com.app.repositories;

import com.app.models.ServiceResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceResponseRepository extends CrudRepository<ServiceResponse, Long> {
    Iterable<ServiceResponse> findAll();
    ServiceResponse findById(Integer id);
}
