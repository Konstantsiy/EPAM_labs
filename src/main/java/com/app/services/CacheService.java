package com.app.services;

import com.app.models.ServiceResponse;
import com.app.models.Equation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CacheService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private HashMap<Equation, ServiceResponse> cache = new HashMap<>();

    public CacheService(){}

    public Map<Equation, ServiceResponse> getAll() {
        return this.cache;
    }

    public ServiceResponse getResponse(Equation equation) {
        if(!(this.cache.isEmpty()) && this.find(equation)) {
            logger.info("Found match request in cache");
            return this.cache.get(equation);
        }
        else {
            logger.info("Save new request in the cache");
            ServiceResponse response = new ServiceResponse();
            response.setEquationRoot(equation.getEquationRoot());
            this.cache.put(equation, response);
            return response;
        }
    }

    public boolean find(Equation equation) {
        if(this.cache.containsKey(equation)) {
            return true;
        }
        return false;
    }

    public void delete(Equation equation) {
        this.cache.remove(equation);
    }

    public void deleteAll() {
        Iterator it = this.cache.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry item = (Map.Entry<Equation, ServiceResponse>) it.next();
            it.remove();
        }
    }

}
