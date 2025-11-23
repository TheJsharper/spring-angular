package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.MultiplicationArgument;
import com.jsharper.dyndns.server.arguments.SubtractionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SubtractionGeneratedArgumentsDynamicTests {

    private final Calculator calculator = new Calculator();


    @TestFactory
    Collection<DynamicTest> dynamicTestsAdditionFromCollection() {
        return Arrays.asList(
                dynamicTest("test dynamic 8 - 2 = 6", () -> assertEquals(6, calculator.subtract(8, 2))),
                dynamicTest("test dynamic 16 - 2 = 14", () -> assertEquals(14, calculator.subtract(16, 2))),
                dynamicTest("test dynamic 80 - 2 = 78", () -> assertEquals(78, calculator.subtract(80, 2))),
                dynamicTest("test dynamic 10 - 2 = 8", () -> assertEquals(8, calculator.subtract(10, 2))),
                dynamicTest("test dynamic 20 - 2 = 18", () -> assertEquals(18, calculator.subtract(20, 2)))
        );
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestDivisionFromStream() {
        return getArguments().map((argument) ->
                dynamicTest(
                        format("test dynamic Minuend(%d) - Subtrahend(%d) = Expected(%d) ", argument.minuend(), argument.subtrahend(), argument.expected()),
                        () -> assertEquals(argument.expected(), calculator.subtract(argument.minuend(), argument.subtrahend())))
        );
    }

    private Stream<SubtractionArgument> getArguments() {
        Random random = new Random();

        return IntStream.iterate(0, (n) -> n + 2).limit(100).mapToObj((_) -> {
            var minuend = random.nextInt(25000, 50000);
            var subtrahend = random.nextInt(255, 10000);
            return new SubtractionArgument(minuend, subtrahend, minuend - subtrahend);
        });


    }


}
