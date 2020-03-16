package com.restserver;

public class ServerResponse {
    private final double firstSlogan;
    private final double resultSum;
    private final double leftBorder;
    private final double rightBorder;
    private final double equationRoot;

    public ServerResponse(double number, double sum, double min, double max) {
        this.firstSlogan = number;
        this.resultSum = sum;
        this.leftBorder = min;
        this.rightBorder = max;
        this.equationRoot = sum - number;
    }

    public double getResultSum() {
        return resultSum;
    }

    public double getFirstSlogan() {
        return firstSlogan;
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public double getEquationRoot() {
        return equationRoot;
    }
}
