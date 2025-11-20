package com.jsharper.dyndns.server.parameterizedClasses;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("square (Number) = Expected")
@ParameterizedClass(name = "[{index}] {arguments}")
@CsvSource(textBlock = """
            9* 3
            16* 4
            21* 4.58257569495584
            32* 5.656854249492381
            """, useHeadersInDisplayName = true, delimiterString = "*")
public record SquareTests(double number, double expected) {
    private static Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testSquare_ResultSquareFromTen_ExpectedThree() {
        //Arrange
        // Parameters

        String failureMessage = format("square could NOT procedure number %f expected %f", number, expected);

        //Act

        double result = calculator.square(number);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

}
