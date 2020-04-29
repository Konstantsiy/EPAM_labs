package com.app.controller;

import com.app.exceptions.InternalServiceException;
import com.app.models.*;
import com.app.services.CacheService;
import com.app.services.EquationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping(value = "/equation")
public class MainController {

    RequestCounter requestCounter = new RequestCounter(0, new Semaphore(1));
    CacheService cacheService = new CacheService();
    EquationService equationService = new EquationService();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/getEquationRoot1")
    public ServiceResponse getEquation(@RequestBody Equation equation) throws InternalServiceException {
        this.equationService.globalVerification(equation);
        this.requestCounter.increaseNumberOfRequests();
        // return found or new response
        return this.cacheService.getCache().entrySet().stream()
                .filter(entry ->  equation.equals(entry.getKey())) // check needed request in cache
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseGet(() -> this.cacheService.add(equation)); // calculates, adds to the cache and returns the result
    }

    @PostMapping(value = "/postEquationList")
    public ArrayList<ServiceResponse> postEquationList(@RequestBody EquationListWrapper equationList)  {
        ArrayList<ServiceResponse> responseList = new ArrayList<ServiceResponse>();
        // check equation list
        equationList.getEquations().stream().forEach(equation -> this.equationService.globalVerification(equation));
        // save requests in the cache if they were not there
        equationList.getEquations().stream().forEach(equation -> {
            if(this.cacheService.find(equation) == false) {
                responseList.add(this.cacheService.add(equation));
            }
        });
        return responseList;
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