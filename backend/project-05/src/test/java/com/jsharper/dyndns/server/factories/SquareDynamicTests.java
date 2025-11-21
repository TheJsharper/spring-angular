package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.SquareArgument;
import com.jsharper.dyndns.server.arguments.SubtractionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SquareDynamicTests {

    private final Calculator calculator = new Calculator();


    @TestFactory
    Collection<DynamicTest> dynamicTestsAdditionFromCollection() {
        return Arrays.asList(
                dynamicTest("test dynamic Square(82) = 9.055385138137417,", () -> assertEquals(9.055385138137417, calculator.square(82))),
                dynamicTest("test dynamic Square(162) = 12.727922061357855", () -> assertEquals(12.727922061357855, calculator.square(162))),
                dynamicTest("test dynamic Square(802) = 28.319604517012593", () -> assertEquals(28.319604517012593, calculator.square(802))),
                dynamicTest("test dynamic Square(102) = 10.099504938362077", () -> assertEquals(10.099504938362077, calculator.square(102))),
                dynamicTest("test dynamic Square(202) = 14.212670403551895", () -> assertEquals(14.212670403551895, calculator.square(202)))
        );
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestDivisionFromStream() {
        return getArguments().map((argument) ->
                dynamicTest(
                        format("test dynamic Square(%f) = Expected(%f) ", argument.number(), argument.expected()),
                        () -> assertEquals(argument.expected(), calculator.square(argument.number())))
        );
    }


    private Stream<SquareArgument> getArguments() {
        return Stream.of(
                new SquareArgument(82, 9.055385138137417),
                new SquareArgument(162, 12.727922061357855),
                new SquareArgument(802, 28.319604517012593),
                new SquareArgument(102, 10.099504938362077),
                new SquareArgument(102, 10.099504938362077),
                new SquareArgument(802, 28.319604517012593),
                new SquareArgument(8002, 89.45389874119518),
                new SquareArgument(62, 7.874007874011811)
        );
    }
}
