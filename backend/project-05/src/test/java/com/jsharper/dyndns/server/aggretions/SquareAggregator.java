package com.jsharper.dyndns.server.aggretions;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.SquareArgument;
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

public class SquareAggregator {
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
    public void testFromCVSParameterizedArguments(@AggregateWith(SquareArgumentsAggregator.class) SquareArgument squareArgument) {
        // Arrange
        var number = squareArgument.number();
        var expected = squareArgument.expected();

        String failureMessage = format("sum could NOT procedure number %f and expected %f", number, expected);

        //Act

        double result = calculator.square(number);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}

class SquareArgumentsAggregator extends SimpleArgumentsAggregator {
    @Override
    protected /*@Nullable*/ SquareArgument aggregateArguments(ArgumentsAccessor accessor, Class<?> targetType, AnnotatedElementContext context, int parameterIndex) throws ArgumentsAggregationException {
        return new SquareArgument(
                Objects.requireNonNull(accessor.getDouble(0)),
                Objects.requireNonNull(accessor.getDouble(1)));
    }
}