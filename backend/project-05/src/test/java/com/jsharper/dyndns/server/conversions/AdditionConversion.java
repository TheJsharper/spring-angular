package com.jsharper.dyndns.server.conversions;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionConversion {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                SummandOne, SummandTwo, Expected
                4, 2, 6
                4, 4, 8
                10, 2, 12
                25, 25, 50
            """, useHeadersInDisplayName = true)
    public void testFromCVSParameterizedArguments(ArgumentsAccessor arguments) {
        // Arrange
        var summandOne = Objects.requireNonNull(arguments.getInteger(0));
        var summandTwo = Objects.requireNonNull(arguments.getInteger(1));
        var expected = Objects.requireNonNull(arguments.getInteger(2));

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
