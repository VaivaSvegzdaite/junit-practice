package com.vsvegzdaite.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void add() {
        var calculator = new Calculator();

        int actual = calculator.add(2,3);

        assertEquals(5, actual);
    }
}