package com.jsharper.dyndns.server.parameterizedClasses;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParameterizedClass(name = "[{index}] {arguments}")
@CsvSource(textBlock = """
                Multiplier, Multiplicand, Expected
                4, 2, 8
                4, 4, 16
                5, 5, 25
            """, useHeadersInDisplayName = true)
public class MultiplicationTests {
    @Parameter(0)
    int multiplier;
    @Parameter(1)
    int multiplicand;
    @Parameter(2)
    int expected;

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }


    @Test
    public void testMultiply_FourDivideTwo_ExpectedEight() {
        //Arrange
        //Parameters

        String failureMessage = format("multiply could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);
        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

}
