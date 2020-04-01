package com.app.service;

import com.app.models.ServiceResponse;
import com.app.models.EquationBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class EquationService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    HashMap<EquationBody, ServiceResponse> equations = new HashMap<>();

    public EquationService(){}

    public Map<EquationBody, ServiceResponse> getAll() {
        return this.equations;
    }

    public boolean find(EquationBody equationBody) {
        if(this.equations.isEmpty()) {
            logger.info("Cache is empty");
            return false;
        }
        if(this.equations.containsKey(equationBody)) {
            logger.info("Cache contains this equation");
            return true;
        }
        else return false;
    }

    public ServiceResponse getValueByKey(EquationBody equationBody) {
        return this.equations.get(equationBody);
    }

    public void save(EquationBody equationBody, ServiceResponse serverResponse) {
        this.equations.put(equationBody, serverResponse);
    }

    public void delete(EquationBody equationBody) {
        this.equations.remove(equationBody);
    }

    public void deleteAll() {
        Iterator it = this.equations.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry item = (Map.Entry<EquationBody, ServiceResponse>) it.next();
            it.remove();
        }
    }

}
