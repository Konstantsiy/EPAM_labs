package com.app.services;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.InternalServiceException;
import com.app.models.Equation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquationService {

    private Logger logger = LoggerFactory.getLogger(EquationService.class);

    public boolean globalVerification(Equation equation) {
        try {
            if(equation.getFirstSlogan() == null || equation.getSum() == null || equation.getMin() == null || equation.getMax() == null) {
                throw new BadRequestException(400, "All parameters are required");
            }
            double equationRoot = equation.getSum() - equation.getFirstSlogan();
            if(equationRoot < equation.getMin() || equationRoot > equation.getMax()) {
                logger.error("Unsuitable conditions for calculations");
                throw new InternalServiceException(500, "The equation root does not fall into the range of values");
            }
        } catch (BadRequestException e) {
            logger.error("Incorrect parameters entered.");
            System.out.println(e.getExceptionCode() + ":" +  e.getMessage());
            return false;
        } catch (InternalServiceException e) {
            logger.error("Unsuitable conditions for calculations");
            System.out.println(e.getExceptionCode() + ":" +  e.getMessage());
            return false;
        }
        logger.info("Verification was successful");
        return true;
    }

    public double calculateEquationRoot(Equation equation) {
        return equation.getSum() - equation.getFirstSlogan();
    }
}
