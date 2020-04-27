package com.app.services;

import com.app.models.ServiceResponse;
import com.app.models.Equation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class CacheService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<Equation, ServiceResponse> cache = new HashMap<>();

    public CacheService(){}

    public Map<Equation, ServiceResponse> getCache() {
        return this.cache;
    }

    public ServiceResponse add(Equation equation) {
        logger.info("Save new request in the cache");
        ServiceResponse response = new ServiceResponse();
        response.setEquationRoot(equation.getEquationRoot());
        this.cache.put(equation, response);
        return response;
    }
}
