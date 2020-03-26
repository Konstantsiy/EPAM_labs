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
    public ServiceResponse processEquation(@RequestParam("firstSlog") Double firstSlog,
                                           @RequestParam("sum") Double resultSum,
                                           @RequestParam("rangeFrom") Double min,
                                           @RequestParam("to") Double max)
                                           throws InternalServiceException, BadRequestException {
        logger.info("Check entered parameters.");
        if(firstSlog == null || resultSum == null || min == null || max == null) {
            logger.error("Incorrect parameters entered.");
            throw new BadRequestException(400, "Bad request exception: all parameters are required");
        }
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
