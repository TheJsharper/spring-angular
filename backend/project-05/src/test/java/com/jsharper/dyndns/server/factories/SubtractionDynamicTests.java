package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.AdditionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class AdditionDynamicTests {

    private final Calculator calculator = new Calculator();


    @TestFactory
    Collection<DynamicTest> dynamicTestsAdditionFromCollection() {
        return Arrays.asList(
                dynamicTest("test dynamic 8+ 2 = 10", () -> assertEquals(10, calculator.sum(8, 2))),
                dynamicTest("test dynamic 16 + 2 = 18", () -> assertEquals(18, calculator.sum(16, 2))),
                dynamicTest("test dynamic 80 + 2 = 82", () -> assertEquals(82, calculator.sum(80, 2))),
                dynamicTest("test dynamic 10 + 2 = 12", () -> assertEquals(12, calculator.sum(10, 2))),
                dynamicTest("test dynamic 20 + 2 = 22", () -> assertEquals(22, calculator.sum(20, 2)))
        );
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestDivisionFromStream() {
        return getArguments().map((argument) ->
                dynamicTest(
                        format("test dynamic SummandOne(%d) + SummandTwo(%d) = Expected(%d) ", argument.summandOne(), argument.summandTwo(), argument.expected()),
                        () -> assertEquals(argument.expected(), calculator.sum(argument.summandOne(), argument.summandTwo())))
        );
    }


    private Stream<AdditionArgument> getArguments() {
        return Stream.of(
                new AdditionArgument(8, 2, 10),
                new AdditionArgument(16, 2, 18),
                new AdditionArgument(80, 2, 82),
                new AdditionArgument(10, 2, 12),
                new AdditionArgument(10, 2, 12),
                new AdditionArgument(80, 2, 82),
                new AdditionArgument(800, 2, 802),
                new AdditionArgument(6, 2, 8)
        );
    }
}
