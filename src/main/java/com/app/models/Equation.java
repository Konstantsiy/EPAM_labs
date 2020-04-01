package com.app.models;


public class Equation {

    private Double firstSlogan;
    private Double sum;
    private Double min;
    private Double max;
    private Double equationRoot;

    public Equation(double slog, double sum, double min, double max) {
        this.firstSlogan = slog;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }

    public void setEquationRoot(Double equationRoot) {
        this.equationRoot = equationRoot;
    }

    public Double getEquationRoot() {
        return this.equationRoot;
    }

    public Double getFirstSlogan() {
        return firstSlogan;
    }

    public Double getSum() {
        return sum;
    }

    public Double getMin() {
        return min;
    }


    public Double getMax() {
        return max;
    }

    public void resetParams(double firstSlogan, double sum, double min, double max) {
        this.firstSlogan = firstSlogan;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }
}
