package com.app.controller;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.InternalServiceException;
import com.app.models.*;
import com.app.repositories.EquationRepository;
import com.app.repositories.ServiceResponseRepository;
import com.app.services.CacheService;
import com.app.services.EquationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping(value = "/equation")
public class MainController {

    @Autowired
    private EquationRepository equationRepository;

    @Autowired
    private ServiceResponseRepository serviceResponseRepository;

    RequestCounter requestCounter = new RequestCounter(0, new Semaphore(1));
    CacheService cacheService = new CacheService();
    EquationService equationService = new EquationService();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "{processId}")
    @Async("asyncExecutor")
    public @ResponseBody CompletableFuture<ServiceResponse> getResponseByProcessId(@PathVariable("processId")Integer processId) throws BadRequestException {
        ServiceResponse response = serviceResponseRepository.findById(processId);
        if(response == null) {
            throw new BadRequestException(400, "There was no response in the database with this id");
        }
        return CompletableFuture.completedFuture(response);
    }

    @GetMapping(value = "/getEquationRoot")
    @Async("asyncExecutor")
    public @ResponseBody CompletableFuture<ServiceResponse> getEquation(@RequestBody Equation equation) throws InternalServiceException {
        this.equationService.globalVerification(equation);
        this.requestCounter.increaseNumberOfRequests();

        Equation neededEquation = equationRepository.findByFirstSloganAndSumAndMinAndMax(
                equation.getFirstSlogan(), equation.getSum(), equation.getMin(), equation.getMax()
        );

        if(neededEquation == null) {
            equationRepository.save(equation);
            ServiceResponse response = new ServiceResponse();
            response.setEquationRoot(this.equationService.calculateEquationRoot(equation));
            serviceResponseRepository.save(response);
            return CompletableFuture.completedFuture(response);
        }
        return CompletableFuture.completedFuture(this.serviceResponseRepository.findById(neededEquation.getId()));
    }

    @PostMapping(value = "/postEquationList")
    @Async("asyncExecutor")
    public @ResponseBody CompletableFuture<Statistics> postEquationList(@RequestBody EquationListWrapper equationList)  {
        ArrayList<ServiceResponse> validResponses = new ArrayList<>();
        Statistics statistics = new Statistics(equationList.getEquations().size());
        equationList.getEquations()
                    .stream()
                    .forEach(equation -> {
                        if(this.equationService.globalVerification(equation)) {
                            statistics.incValid();
                            ServiceResponse response = new ServiceResponse();
                            response.setEquationRoot(this.equationService.calculateEquationRoot(equation));
                            validResponses.add(response);
                            statistics.compare(response.getEquationRoot());

                            Equation neededEquation = equationRepository.findByFirstSloganAndSumAndMinAndMax(
                                    equation.getFirstSlogan(), equation.getSum(), equation.getMin(), equation.getMax()
                            );

                            if(neededEquation == null) {
                                equationRepository.save(equation);
                                serviceResponseRepository.save(response);
                                statistics.incUnique();
                            }
                        }
                    });
        double mostPopularResponse = 0;
        int mostPopularFreq = 0;
        boolean flagForMin = false, flagForMax = false;
        for (ServiceResponse response : validResponses) {
            if(!flagForMax) {
                flagForMax = true;
                statistics.setMax(response.getEquationRoot());
            } else if(statistics.getMax() < response.getEquationRoot()) {
                statistics.setMax(response.getEquationRoot());
            }
            if(!flagForMin) {
                flagForMin = true;
                statistics.setMin(response.getEquationRoot());
            } else if(statistics.getMin() > response.getEquationRoot()) {
                statistics.setMin(response.getEquationRoot());
            }
            int responseFreq = Collections.frequency(validResponses, response);
            if(responseFreq > mostPopularFreq) {
                mostPopularFreq = responseFreq;
                mostPopularResponse = response.getEquationRoot();
            }
        }
        statistics.setMostPopular(mostPopularResponse);
        return CompletableFuture.completedFuture(statistics);
    }

    @GetMapping(value = "/getRequestsFromBd")
    public @ResponseBody Iterable<Equation> getAllRequestsFromBd() {
        return equationRepository.findAll();
    }

    @GetMapping(value = "/getResponsesFromBd")
    public @ResponseBody Iterable<ServiceResponse> getAllResponsesFromBd() { return serviceResponseRepository.findAll(); }

    @GetMapping(value = "/requestCounter")
    public int getCounter() {
        return this.requestCounter.getCounter();
    }
}