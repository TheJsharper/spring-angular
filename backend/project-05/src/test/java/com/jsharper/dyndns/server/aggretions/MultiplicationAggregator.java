package com.jsharper.dyndns.server.aggretions;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.MultiplicationArgument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AnnotatedElementContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.SimpleArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationAggregator {

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
    public void testFromCVSParameterizedArguments(@AggregateWith(MultiplicationArgumentsAggregator.class) MultiplicationArgument multiplicationArgument) {
        // Arrange
        var multiplier = multiplicationArgument.multiplier();
        var multiplicand = multiplicationArgument.multiplicand();
        var expected = multiplicationArgument.expected();

        String failureMessage = format("sum could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);

        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}

class MultiplicationArgumentsAggregator extends SimpleArgumentsAggregator {
    @Override
    protected /*@Nullable*/ MultiplicationArgument aggregateArguments(ArgumentsAccessor accessor, Class<?> targetType, AnnotatedElementContext context, int parameterIndex) throws ArgumentsAggregationException {

        return new MultiplicationArgument(
                Objects.requireNonNull(accessor.getInteger(0)),
                Objects.requireNonNull(accessor.getInteger(1)),
                Objects.requireNonNull(accessor.getInteger(2)));

    }
}
