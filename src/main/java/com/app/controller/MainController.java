package com.app.controller;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.InternalServiceException;
import com.app.models.*;
import com.app.repositories.EquationRepository;
import com.app.repositories.ServiceResponseRepository;
import com.app.repositories.StatisticsRepository;
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

    @Autowired
    private StatisticsRepository statisticsRepository;

    RequestCounter requestCounter = new RequestCounter(0, new Semaphore(1));
    EquationService equationService = new EquationService();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/getStatistics/{processId}")
    public @ResponseBody Statistics getResponseByProcessId(@PathVariable("processId")Integer processId) throws BadRequestException {
        Statistics statistics = statisticsRepository.findByProcessId(processId);
        if(statistics == null) {
            throw new BadRequestException(400, "There was no process in the database with this id");
        }
        return statistics;
    }

    @Async("asyncExecutor")
    @GetMapping(value = "/getEquationRoot")
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

    @Async("asyncExecutor")
    @PostMapping(value = "postEquationList")
    public CompletableFuture<Integer> postEquationList(@RequestBody EquationListWrapper equationListWrapper) throws InternalServiceException {

        Statistics statistics = new Statistics();
        statistics.setProcessId((int) equationRepository.count() + 1);
        statistics.setTotalCount(equationListWrapper.getEquations().size());

        ArrayList<ServiceResponse> validResponses = new ArrayList<>();
        equationListWrapper.getEquations()
                           .stream()
                           .forEach(equation -> {
                               if(this.equationService.globalVerification(equation)) {
                                   statistics.incValidNumber();
                                   ServiceResponse response = new ServiceResponse();
                                   response.setEquationRoot(this.equationService.calculateEquationRoot(equation));
                                   validResponses.add(response);
                                   statistics.compare(response.getEquationRoot());

                                   Equation neededEquation = equationRepository.findByFirstSloganAndSumAndMinAndMax(
                                           equation.getFirstSlogan(), equation.getSum(), equation.getMin(), equation.getMax()
                                   );

                                   if(neededEquation == null) {
                                       statistics.incUniqueNumber();
                                       equationRepository.save(equation);
                                       serviceResponseRepository.save(response);
                                   }
                               }
                           });
        double mostPopularResponse = 0;
        int mostPopularFreq = 0;
        for (ServiceResponse response : validResponses) {
            int responseFreq = Collections.frequency(validResponses, response);
            if(responseFreq > mostPopularFreq) {
                mostPopularFreq = responseFreq;
                mostPopularResponse = response.getEquationRoot();
            }
        }
        statistics.setPopularValue(mostPopularResponse);
        statisticsRepository.save(statistics);
        return CompletableFuture.completedFuture(statistics.getProcessId());
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