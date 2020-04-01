package com.app.controller;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.InternalServiceException;
import com.app.models.EquationBody;
import com.app.models.ServiceResponse;
import com.app.service.EquationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping(value = "/equation")
public class MainController {

    EquationService equationService = new EquationService();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ServiceResponse processEquation(@RequestParam("firstSlog") Double firstSlog,
                                           @RequestParam("sum") Double resultSum,
                                           @RequestParam("rangeFrom") Double min,
                                           @RequestParam("to") Double max) throws InternalServiceException, BadRequestException {
        EquationBody equation = new EquationBody(firstSlog, resultSum, min, max);
        equation.verification();
        logger.info("The calculations were successful");
        if(this.equationService.find(equation)) {
            logger.info("Found match in cache");
            return this.equationService.getValueByKey(equation);
        }
        logger.info("Put a new request in the cache");
        ServiceResponse response = new ServiceResponse();
        response.setEquationRoot(equation.getEquationRoot());
        this.equationService.save(equation, response);
        return response;
    }

    @GetMapping(value = "/cache")
    public Map<EquationBody, ServiceResponse> getCache() {
        logger.info("Get all requests");
        return this.equationService.getAll();
    }

    @DeleteMapping(value = "/cache/delete")
    public Map<EquationBody, ServiceResponse> deleteEquation(@RequestParam("firstSlog") Double firstSlogan,
                               @RequestParam("sum") Double sum,
                               @RequestParam("rangeFrom") Double min,
                               @RequestParam("max") Double max) throws BadRequestException, InternalServiceException {
        EquationBody equation = new EquationBody(firstSlogan, sum, min, max);
        equation.verification();
        if(this.equationService.find(equation)) {
            this.equationService.delete(equation);
            logger.info("Request removed from cache");
        }
        else {
            logger.error("No such request in cache");
        }
        return this.equationService.getAll();
    }

    @DeleteMapping(value = "/cache/deleteAll")
    public Map<EquationBody, ServiceResponse> deleteAllEquations() {
        this.equationService.deleteAll();
        logger.info("The cache is cleared");
        return this.equationService.getAll();
    }
}
