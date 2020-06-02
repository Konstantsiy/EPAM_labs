package com.app.repositories;

import com.app.models.ServiceResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;



@Repository
public interface ServiceResponseRepository extends CrudRepository<ServiceResponse, Long> {
    @Async("asyncExecutor") Iterable<ServiceResponse> findAll();

    @Async("asyncExecutor")
    Iterable<ServiceResponse> findAllById(Integer id);

    @Async("asyncExecutor")
    ServiceResponse findById(Integer id);
}
