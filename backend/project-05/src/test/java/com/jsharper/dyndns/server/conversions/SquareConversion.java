package com.jsharper.dyndns.server.conversions;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SquareConversion {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                Number, Expected
                 9, 3
            16, 4
            21, 4.58257569495584
            32, 5.656854249492381
            """, useHeadersInDisplayName = true)
    public void testFromCVSParameterizedArguments(ArgumentsAccessor arguments) {
        // Arrange
        double number = Objects.requireNonNull(arguments.getDouble(0));
        double expected = Objects.requireNonNull(arguments.getDouble(1));

        String failureMessage = format("sum could NOT procedure number %f and expected %f", number, expected);

        //Act

        double result =  calculator.square(number);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
