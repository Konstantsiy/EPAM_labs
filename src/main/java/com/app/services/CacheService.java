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

    public CacheService() {}

    public Map<Equation, ServiceResponse> getCache() {
        return this.cache;
    }

    public boolean find(Equation equation) {
        if (this.cache.containsKey(equation)) return true;
        else return false;
    }

    public void add(Equation equation, ServiceResponse serviceResponse) {
        logger.info("Save new request in the cache");
        this.cache.put(equation, serviceResponse);
    }
}
