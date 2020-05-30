package com.app.models;


import javax.persistence.*;

@Entity
@Table(name = "responses_table")
public class ServiceResponse {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "equation_root")
    private Double equationRoot;

    public ServiceResponse() {}

    public void setEquationRoot(Double equationRoot) {
        this.equationRoot = equationRoot;
    }

    public Double getEquationRoot() {
        return equationRoot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
