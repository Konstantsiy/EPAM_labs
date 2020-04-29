package com.app.models;

public class ServiceResponse {
    private double equationRoot;

    public ServiceResponse() {};


    public ServiceResponse(double root) {
        this.equationRoot = root;
    };

    public void setEquationRoot(double equationRoot) {
        this.equationRoot = equationRoot;
    }

    public double getEquationRoot() {
        return equationRoot;
    }

}
