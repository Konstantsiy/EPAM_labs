package com.app.models;


public class Equation {
    private Double firstSlogan;
    private Double sum;
    private Double min;
    private Double max;
    private Double equationRoot;

    public Equation(){}

    public void setEquationRoot(Double equationRoot) {
        this.equationRoot = equationRoot;
    }

    public void setFirstSlogan(Double firstSlogan){
        this.firstSlogan = firstSlogan;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public void setMax(Double max) {
        this.max = max;
    }
    public Double getFirstSlogan() {
        return firstSlogan;
    }

    public Double getSum() {
        return sum;
    }

    public Double getEquationRoot() {
        return this.equationRoot;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }
}
