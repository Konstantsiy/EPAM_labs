package com.app.models;


public class Statistics {
    private int count;
    private int unique;
    private int valid;
    private double min;
    private double max;
    private double mostPopular;

    public Statistics(int count) {
        this.count = count;
        this.valid = this.unique = 0;
        this.min = this.max  = 0;
    }

    public void incValid() {this.valid++;}
    public void incUnique() {this.unique++;}

    public void compare(double value) {
        if(value > this.max) {
            this.max = value;
        }
        if(value < this.max) {
            this.min = value;
        }
    }

    public void setMostPopular(double value) {
        this.mostPopular = value;
    }

    public int getCount() {
        return count;
    }

    public int getUnique() {
        return unique;
    }

    public int getValid() {
        return valid;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMostPopular() {
        return mostPopular;
    }
}
