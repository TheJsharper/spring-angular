package com.jsharper.dyndns.server.aggretions;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.AdditionArgument;
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

public class AdditionAggregator {

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
    public void testFromCVSParameterizedArguments(@AggregateWith(AdditionArgumentAggregator.class) AdditionArgument additionArgument) {
        // Arrange
        var summandOne = additionArgument.summandOne();
        var summandTwo = additionArgument.summandTwo();
        var expected = additionArgument.expected();

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
class AdditionArgumentAggregator  extends SimpleArgumentsAggregator {
    @Override
    protected @Nullable AdditionArgument aggregateArguments(ArgumentsAccessor accessor,
                                                            Class<?> targetType,
                                                            AnnotatedElementContext context,
                                                            int parameterIndex) throws ArgumentsAggregationException {

        return new AdditionArgument(
                Objects.requireNonNull(accessor.getInteger(0)),
                Objects.requireNonNull(accessor.getInteger(1)),
                Objects.requireNonNull(accessor.getInteger(2)));
    }
}