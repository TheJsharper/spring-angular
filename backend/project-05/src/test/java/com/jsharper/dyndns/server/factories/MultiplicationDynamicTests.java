package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.MultiplicationArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.*;

public class MultiplicationDynamicTests {
    private final Calculator calculator = new Calculator();



    @TestFactory
    Stream<DynamicTest> dynamicTestsMultiplicationFromStream() {
        return getInitMultiplicationTestCases().map((argument) ->
                dynamicTest(format("test dynamic Multiplier(%d) * Multiplicand(%d) = Expected(%d) ", argument.multiplier(), argument.multiplicand(), argument.expected()), () -> assertEquals(argument.expected(), calculator.multiply(argument.multiplier(), argument.multiplicand())))
        );
    }

    private Stream<MultiplicationArgument> getInitMultiplicationTestCases(){
        return Stream.of(
                new MultiplicationArgument(2, 2, 4),
                new MultiplicationArgument(4, 2, 8),
                new MultiplicationArgument(8, 2, 16),
                new MultiplicationArgument(20, 2, 40),
                new MultiplicationArgument(200, 2, 400),
                new MultiplicationArgument(5, 5, 25),
                new MultiplicationArgument(10, 2, 20)
        );
    }
}
