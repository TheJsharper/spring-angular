package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.DivisionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class DivisionDynamicTests {
    private final Calculator calculator = new Calculator();

    @TestFactory
    Collection<DynamicTest> dynamicTestsDivisionFromCollection() {
        return Arrays.asList(
                dynamicTest("test dynamic 8/2", () -> assertEquals(4, calculator.divide(8, 2))),
                dynamicTest("test dynamic 16/2", () -> assertEquals(8, calculator.divide(16, 2))),
                dynamicTest("test dynamic 80/2", () -> assertEquals(40, calculator.divide(80, 2))),
                dynamicTest("test dynamic 10/2", () -> assertEquals(5, calculator.divide(10, 2))),
                dynamicTest("test dynamic 20/2", () -> assertEquals(10, calculator.divide(20, 2)))
        );
    }


    @TestFactory
    Stream<DynamicTest> dynamicTestDivisionFromStream(){
        return  getArguments().map( (argument)->
                dynamicTest(
                        format("test dynamic Dividend(%d) * Divisor(%d) = Expected(%d) ", argument.dividend(), argument.divisor(), argument.expected()),
                        () -> assertEquals(argument.expected(), calculator.divide(argument.dividend(), argument.divisor())))
        );
    }

    private Stream<DivisionArgument> getArguments(){
        return Stream.of(
                new DivisionArgument(8, 2, 4),
                new DivisionArgument(16, 2, 8),
                new DivisionArgument(80, 2, 40),
                new DivisionArgument(10, 2, 5),
                new DivisionArgument(10, 2, 5),
                new DivisionArgument(80, 2, 40),
                new DivisionArgument(800, 2, 400),
                new DivisionArgument(6, 2, 3)
        );
    }
}
