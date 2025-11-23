package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.MultiplicationArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MultiplicationGeneratedArgumentsDynamicTests {
    private final Calculator calculator = new Calculator();


    @TestFactory
    Stream<DynamicTest> dynamicTestsMultiplicationFromStream() {
        return getArguments().map((argument) ->
                dynamicTest(format("test dynamic Multiplier(%d) * Multiplicand(%d) = Expected(%d) ", argument.multiplier(), argument.multiplicand(), argument.expected()), () -> assertEquals(argument.expected(), calculator.multiply(argument.multiplier(), argument.multiplicand())))
        );
    }


    private Stream<MultiplicationArgument> getArguments() {
        Random random = new Random();

        return IntStream.iterate(0, (n) -> n + 2).limit(100).mapToObj((_) -> {
            var multiplier = random.nextInt(25000, 50000);
            var multiplicand = random.nextInt(255, 10000);
            return new MultiplicationArgument(multiplier, multiplicand, multiplier * multiplicand);
        });


    }
}
