package com.app.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equations_table")
public class Equation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_slogan")
    private Double firstSlogan;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "min")
    private Double min;

    @Column(name = "max")
    private Double max;

    public Equation(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return Objects.equals(firstSlogan, equation.firstSlogan) &&
                Objects.equals(sum, equation.sum) &&
                Objects.equals(min, equation.min) &&
                Objects.equals(max, equation.max);
    }

    @Override
    public String toString() {
        return "Equation{" +
                "id=" + this.id +
                ", first_slogan=" + this.firstSlogan +
                ", sum=" + this.sum +
                ", min=" + this.min +
                ", max=" + this.max + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstSlogan, sum, min, max);
    }

    public Equation(Double firstSlogan, Double sum, Double min, Double max) {
        this.firstSlogan = firstSlogan;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }

    public Equation(Equation equation) {
        this.firstSlogan = equation.firstSlogan;
        this.sum = equation.sum;
        this.max = equation.max;
        this.min = equation.min;
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

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id;
    }
}
