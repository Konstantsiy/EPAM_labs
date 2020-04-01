package com.app.services;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.InternalServiceException;
import com.app.models.Equation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquationService {

    private Logger logger = LoggerFactory.getLogger(EquationService.class);

    public void globalVerification(Equation equation) throws BadRequestException, InternalServiceException {
        if(!this.parametersVerification(equation)) {
            logger.error("Incorrect parameters entered.");
            throw new BadRequestException(400, "All parameters are required");
        }
        if(!this.calculationVerification(equation)) {
            logger.error("Unsuitable conditions for calculations");
            throw new InternalServiceException(500, "The equation root does not fall into the range of values");
        }
        equation.setEquationRoot(equation.getSum() - equation.getFirstSlogan());
    }

    public boolean parametersVerification(Equation equation) {
        if(equation.getFirstSlogan() == null || equation.getSum() == null || equation.getMin() == null || equation.getMax() == null) {
            return false;
        }
        else return true;
    }

    public boolean calculationVerification(Equation equation) {
        double equationRoot = equation.getSum() - equation.getFirstSlogan();
        if(equationRoot < equation.getMin() || equationRoot > equation.getMax()) {
            logger.error("Unsuitable conditions for calculations");
            return false;
        }
        else return true;
    }
}
