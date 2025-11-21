package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.AdditionArgument;
import com.jsharper.dyndns.server.arguments.SubtractionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SubtractionDynamicTests {

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
        return Stream.of(
                new SubtractionArgument(8, 2, 6),
                new SubtractionArgument(16, 2, 14),
                new SubtractionArgument(80, 2, 78),
                new SubtractionArgument(10, 2, 8),
                new SubtractionArgument(10, 2, 8),
                new SubtractionArgument(80, 2, 78),
                new SubtractionArgument(800, 2, 798),
                new SubtractionArgument(6, 2, 4)
        );
    }
}
