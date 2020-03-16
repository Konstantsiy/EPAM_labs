package com.restserver;

public class Calculation {
    private final double firstSlogan;
    private final double resultSum;
    private final double leftBorder;
    private final double rightBorder;
    private final double equationRoot;


    public Calculation(double number, double resultSum, double min, double max) {
        this.firstSlogan = number;
        this.resultSum = resultSum;
        this.rightBorder = max;
        this.leftBorder = min;
        this.equationRoot = this.resultSum - this.firstSlogan;
    }

    public double getEquationRoot() {
        return this.resultSum - this.firstSlogan;
    }

}
