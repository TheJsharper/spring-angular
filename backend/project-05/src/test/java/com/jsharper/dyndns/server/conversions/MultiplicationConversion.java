package com.jsharper.dyndns.server.conversions;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationConversion {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                Multiplier, Multiplicand, Expected
                4, 2, 8
                4, 4, 16
                10, 2, 20
                25, 25, 625
            """, useHeadersInDisplayName = true)
    public void testFromCVSParameterizedArguments(ArgumentsAccessor arguments) {
        // Arrange
        var multiplier = Objects.requireNonNull(arguments.getInteger(0));
        var multiplicand = Objects.requireNonNull(arguments.getInteger(1));
        var expected = Objects.requireNonNull(arguments.getInteger(2));

        String failureMessage = format("sum could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);

        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
