package com.app.models;

import javax.persistence.*;

@Entity
@Table(name = "statistics_table")
public class Statistics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "process_id")
    private Integer processId;

    @Column(name = "total_count")
    private Integer totalCount;

    @Column(name = "unique_number")
    private Integer uniqueNumber;

    @Column(name = "valid_number")
    private Integer validNumber;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "popular_value")
    private Double popularValue;

    public Statistics() {
        setMaxValue((double)0);
        setMinValue((double)0);
        setUniqueNumber(0);
        setValidNumber(0);
    }

    public void incValidNumber() {this.validNumber++;}
    public void incUniqueNumber() {this.uniqueNumber++;}

    public void compare(double value) {
        if(value > this.maxValue) {
            this.maxValue = value;
        }
        if(value < this.minValue) {
            this.minValue = value;
        }
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Integer uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public Integer getValidNumber() {
        return validNumber;
    }

    public void setValidNumber(Integer validNumber) {
        this.validNumber = validNumber;
    }

    public Double getPopularValue() {
        return popularValue;
    }

    public void setPopularValue(Double popularValue) {
        this.popularValue = popularValue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
