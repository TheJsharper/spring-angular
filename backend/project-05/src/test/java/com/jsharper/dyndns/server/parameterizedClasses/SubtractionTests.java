package com.jsharper.dyndns.server.parameterizedClasses;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParameterizedClass(name = "[{index}] {arguments}")
@DisplayName("subtract Minuend - Subtrahend = Expected")
@CsvSource(useHeadersInDisplayName = true, textBlock = """ 
            Minuend| Subtrahend| expected
            4|2| 2
            4|4| 0
            10| 2| 8
            25| 5| 20
            """, delimiterString = "|")
public record SubtractionTests(int minuend, int subtrahend, int expected) {

    private static Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }
    @Test
    public void testSubtract_FourDivideTwo_ExpectedTwo() {
        //Arrange
        //Parameter
        String failureMessage = format("subtract could NOT procedure minuend %d and subtrahend %d and expected %d", minuend, subtrahend, expected);


        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

}
