package com.app.models;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.InternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquationBody {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private double firstSlogan;
    private double sum;
    private double min;
    private double max;

    EquationBody() {};

    public EquationBody(double slog, double sum, double min, double max) {
        this.firstSlogan = slog;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }

    public double getEquationRoot() {
        return this.sum - this.firstSlogan;
    }

    public void verification() throws BadRequestException, InternalServiceException {
        if(!this.parametersVerification()) {
            logger.error("Incorrect parameters entered.");
            throw new BadRequestException(400, "All parameters are required");
        }
        if(!this.calculationVerification()) {
            logger.error("Unsuitable conditions for calculations");
            throw new InternalServiceException(500, "The equation root does not fall into the range of values");
        }
    }

    public boolean parametersVerification() {
        if((Double)this.firstSlogan == null || (Double)this.sum == null || (Double)this.min == null || (Double)this.max == null) {
            return false;
        }
        return true;
    }

    public boolean calculationVerification() {
        double root = this.sum - this.firstSlogan;
        if(root < this.min || root > this.max) {
            return false;
        }
        return true;
    }

    public double getFirstSlogan() {
        return firstSlogan;
    }

    public double getSum() {
        return sum;
    }

    public double getMin() {
        return min;
    }


    public double getMax() {
        return max;
    }

    public void setAllParams(double firstSlogan, double sum, double min, double max) {
        this.firstSlogan = firstSlogan;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }
}
