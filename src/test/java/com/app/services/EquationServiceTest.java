package com.app.services;

import com.app.models.Equation;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquationServiceTest {

    @BeforeTestClass
    public static void beforeTestClass() {
        System.out.println("Before EquationServiceTest.class");
    }
    EquationService equationService = new EquationService();
    Equation equation = new Equation(2, 12, -10, 30);

    @Test
    void parametersVerification() {
        assertEquals(true, equationService.parametersVerification(equation));
    }

    @Test
    void calculationVerification() {
        assertEquals(true, equationService.calculationVerification(equation));
        equation.resetParams(0, 80, 1, 10);
        assertEquals(false, equationService.calculationVerification(equation));
    }

    @AfterTestClass
    public static void afterTestClass() {
        System.out.println("After EquationServiceTest.class");
    }
}