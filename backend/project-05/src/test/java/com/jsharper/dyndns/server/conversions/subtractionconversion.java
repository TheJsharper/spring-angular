package com.jsharper.dyndns.server.conversions;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class subtractionconversion {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                Minuend, Subtrahend, Expected
                4, 2, 2
                4, 4, 0
                10, 2, 8
                25, 25, 0
            """, useHeadersInDisplayName = true)
    public void testFromCVSParameterizedArguments(ArgumentsAccessor arguments) {
        // Arrange
        var minuend = Objects.requireNonNull(arguments.getInteger(0));
        var subtrahend = Objects.requireNonNull(arguments.getInteger(1));
        var expected = Objects.requireNonNull(arguments.getInteger(2));

        String failureMessage = format("sum could NOT procedure minuend %d and  subtrahend %d and expected %d", minuend, subtrahend, expected);

        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
