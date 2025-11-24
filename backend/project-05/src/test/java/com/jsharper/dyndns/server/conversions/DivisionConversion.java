package com.jsharper.dyndns.server.conversions;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DivisionConversion {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                Dividend, Divisor, Expected
                4, 2, 2
                4, 4, 1
                10, 2, 5
                25, 25, 1
            """, useHeadersInDisplayName = true)
    public void testFromCVSParameterizedArguments(ArgumentsAccessor arguments) {
        // Arrange
        var dividend = Objects.requireNonNull(arguments.getInteger(0));
        var divisor = Objects.requireNonNull(arguments.getInteger(1));
        var expected = Objects.requireNonNull(arguments.getInteger(2));

        String failureMessage = format("sum could NOT procedure dividend %d and  divisor %d and expected %d", dividend, divisor, expected);

        //Act

        int result = calculator.divide(dividend, divisor);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
