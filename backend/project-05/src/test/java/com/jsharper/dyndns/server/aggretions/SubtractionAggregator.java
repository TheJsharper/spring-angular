package com.jsharper.dyndns.server.aggretions;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.SubtractionArgument;
import org.jspecify.annotations.Nullable;
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

public class SubtractionAggregator {
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
    public void testFromCVSParameterizedArguments(@AggregateWith(SubtractionArgumentsAggregator.class) SubtractionArgument subtractionArgument) {
        // Arrange
        var minuend = subtractionArgument.minuend();
        var subtrahend = subtractionArgument.subtrahend();
        var expected = subtractionArgument.expected();

        String failureMessage = format("sum could NOT procedure minuend %d and  subtrahend %d and expected %d", minuend, subtrahend, expected);

        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}

class SubtractionArgumentsAggregator extends SimpleArgumentsAggregator {
    @Override
    protected @Nullable SubtractionArgument aggregateArguments(ArgumentsAccessor accessor,
                                                               Class<?> targetType,
                                                               AnnotatedElementContext context,
                                                               int parameterIndex) throws ArgumentsAggregationException {
        return new SubtractionArgument(
                Objects.requireNonNull(accessor.getInteger(0)),
                Objects.requireNonNull(accessor.getInteger(1)),
                Objects.requireNonNull(accessor.getInteger(2)));
    }
}
