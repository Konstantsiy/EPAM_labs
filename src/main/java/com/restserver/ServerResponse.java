package com.restserver;

public class ServerResponse {
    private double equationRoot;
    private String comment;

    public ServerResponse() {};

    public ServerResponse(double root, String comment) {
        this.equationRoot = root;
        this.comment = comment;
    };

    public void setEquationRoot(double equationRoot) {
        this.equationRoot = equationRoot;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getEquationRoot() {
        return equationRoot;
    }

    public String getComment() {
        return comment;
    }
}
