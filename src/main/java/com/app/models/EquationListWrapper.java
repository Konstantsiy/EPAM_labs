package com.app.models;


import java.util.ArrayList;
import java.util.List;

public class EquationListWrapper {
    private List<Equation> equations = new ArrayList<Equation>();

    public EquationListWrapper() {}

    public List<Equation> getEquations() {
        return equations;
    }

    public void setEquations(List<Equation> equations) {
        this.equations = equations;
    }
}
