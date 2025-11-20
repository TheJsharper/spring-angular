package com.jsharper.dyndns.server.parameterizedClasses;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParameterizedClass
@CsvSource({
        "4, 2, 2",
        "8, 2, 4",
        "16, 2, 8",
        "16, 4, 4",
})
public record DivisionTests(int dividend, int divisor, int expected) {
    private static Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
     void testDivide_FourDivideTwo_ExpectedTwo() {
        //Arrange
        // Parameters

        String failureMessage = format("divide could NOT procedure dividend %d and divisor %d and expected %d", dividend, divisor, expected);

        //Act

        int result = calculator.divide(dividend, divisor);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

}
