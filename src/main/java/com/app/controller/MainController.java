package com.app.controller;

import com.app.exceptions.InternalServiceException;
import com.app.models.*;
import com.app.services.CacheService;
import com.app.services.EquationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping(value = "/equation")
public class MainController {

    RequestCounter requestCounter = new RequestCounter(0, new Semaphore(1));
    CacheService cacheService = new CacheService();
    EquationService equationService = new EquationService();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/getEquationRoot")
    public ServiceResponse getEquation(@RequestBody Equation equation) throws InternalServiceException {
        this.equationService.globalVerification(equation);
        this.requestCounter.increaseNumberOfRequests();
        return this.cacheService.getCache()
                                .entrySet()
                                .stream()
                                .filter(entry ->  equation.equals(entry.getKey()))
                                .findFirst()
                                .map(Map.Entry::getValue)
                                .orElseGet(() -> {
                                    ServiceResponse response = new ServiceResponse();
                                    response.setEquationRoot(this.equationService.calculateEquationRoot(equation));
                                    this.cacheService.add(equation, response);
                                    return response;
                                });
    }

    @PostMapping(value = "/postEquationList")
    public Statistics postEquationList(@RequestBody EquationListWrapper equationList)  {
        ArrayList<ServiceResponse> responses = new ArrayList<>();
        Statistics statistics = new Statistics(equationList.getEquations().size());
        equationList.getEquations()
                    .stream()
                    .forEach(equation -> {
                        if(this.equationService.globalVerification(equation)) {
                            statistics.incValid();

                            ServiceResponse response = new ServiceResponse();
                            response.setEquationRoot(this.equationService.calculateEquationRoot(equation));
                            responses.add(response);
                            statistics.compare(response.getEquationRoot());

                            if(!this.cacheService.find(equation)) {
                                statistics.incUnique();
                                this.cacheService.add(equation, response);
                            }
                        }
                    });
        double mostPopularResponse = 0;
        int mostPopularFreq = 0;
        for (ServiceResponse response : responses) {
            int responseFreq = Collections.frequency(responses, response);
            if(responseFreq > mostPopularFreq) {
                mostPopularFreq = responseFreq;
                mostPopularResponse = response.getEquationRoot();
            }
        }
        statistics.setMostPopular(mostPopularResponse);
        return statistics;
    }

    @GetMapping(value = "/cache")
    public Map<Equation, ServiceResponse> getCache() throws InternalServiceException {
        this.requestCounter.increaseNumberOfRequests();
        logger.info("Get all requests");
        return this.cacheService.getCache();
    }

    @DeleteMapping(value = "/cache/delete")
    public void deleteEquation(@RequestBody Equation equation) throws InternalServiceException {
        this.equationService.globalVerification(equation);
        this.requestCounter.increaseNumberOfRequests();
        this.cacheService.getCache().entrySet().stream()
                .filter(entry -> entry.getKey().equals(equation))
                .findFirst()
                .filter(entry -> {
                    logger.info("Request removed from cache");
                    this.cacheService.getCache().remove(entry.getKey());
                    return true;
                })
                .orElseGet(() -> {
                    logger.error("No such request in cache");
                    return null;
                });
    }

    @DeleteMapping(value = "/cache/deleteAll")
    public void deleteAllEquations() throws InternalServiceException {
        this.requestCounter.increaseNumberOfRequests();
        this.cacheService.getCache().clear();
        logger.info("The cache is cleared");
    }

    @GetMapping(value = "/requestCounter")
    public int getCounter() {
        return this.requestCounter.getCounter();
    }
}
