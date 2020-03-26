package com.restserver;

public class EquationBody {

    private double firstSlogan;
    private double sum;
    private double min;
    private double max;

    EquationBody() {};

    EquationBody(double slog, double sum, double min, double max) {
        this.firstSlogan = slog;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }

    public double getEquationRoot() {
        return this.sum - this.firstSlogan;
    }

    public boolean verification() {
        double root = this.sum - this.firstSlogan;
        if(root < this.min || root > this.max) return false;
        return true;
    }

    public double getFirstSlogan() {
        return firstSlogan;
    }

    public void setFirstSlogan(double firstSlogan) {
        this.firstSlogan = firstSlogan;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setAllParams(double firstSlogan, double sum, double min, double max) {
        this.firstSlogan = firstSlogan;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }
}
