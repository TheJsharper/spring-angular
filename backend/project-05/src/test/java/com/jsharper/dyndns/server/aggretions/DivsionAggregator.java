package com.jsharper.dyndns.server.aggretions;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.AdditionArgument;
import com.jsharper.dyndns.server.arguments.DivisionArgument;
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

public class DivsionAggregator {

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
    public void testFromCVSParameterizedArguments(@AggregateWith(DivisionArgumentAggregator.class) DivisionArgument additionArgument) {
        // Arrange
        var dividend = additionArgument.dividend();
        var divisor = additionArgument.divisor();
        var expected = additionArgument.expected();

        String failureMessage = format("sum could NOT procedure dividend %d and  divisor %d and expected %d", dividend, divisor, expected);

        //Act

        int result = calculator.divide(dividend, divisor);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
class DivisionArgumentAggregator  extends SimpleArgumentsAggregator {
    @Override
    protected @Nullable DivisionArgument aggregateArguments(ArgumentsAccessor accessor,
                                                            Class<?> targetType,
                                                            AnnotatedElementContext context,
                                                            int parameterIndex) throws ArgumentsAggregationException {

        return new DivisionArgument(
                Objects.requireNonNull(accessor.getInteger(0)),
                Objects.requireNonNull(accessor.getInteger(1)),
                Objects.requireNonNull(accessor.getInteger(2)));
    }
}