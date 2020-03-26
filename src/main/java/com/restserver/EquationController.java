package com.restserver;

import Exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/equation")
public class EquationController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ServiceResponse processEquation(@RequestParam("firstSlog") double firstSlog,
                                           @RequestParam("sum") double resultSum,
                                           @RequestParam("rangeFrom") double min,
                                           @RequestParam("to") double max) throws InternalServiceException {
        EquationBody equation = new EquationBody(firstSlog, resultSum, min, max);
        if(!equation.verification()) {
            logger.error("Unsuitable conditions for calculations");
            throw new InternalServiceException(500, "Internal service exception: the equation root does not fall into the range of values");
        }
        logger.info("The calculations were successful");
        ServiceResponse response = new ServiceResponse();
        response.setEquationRoot(equation.getEquationRoot());
        logger.info("The response of the service has formed");
        return response;
    }
}
