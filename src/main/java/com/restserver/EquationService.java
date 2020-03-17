package com.restserver;

public class EquationService {
    private final double firstSlogan;
    private String comment;
    private final double sum;
    private final double min;
    private final double max;


    public EquationService(double number, double sum, double min, double max) {
        this.firstSlogan = number;
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.comment = "ok";
    }

    public double getEquationRoot() {
        double number = this.sum - this.firstSlogan;
        if(number < this.min || number > this.max) {
            this.comment = "there is no root in user range";
        }
        return number;
    }

    public String getComment() {
        return comment;
    }
}
