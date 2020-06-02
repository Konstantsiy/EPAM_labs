package com.app.repositories;

import com.app.models.Statistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

public interface StatisticsRepository extends CrudRepository<Statistics, Long> {
    Statistics findByProcessId(Integer id);
}
