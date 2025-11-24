package com.jsharper.dyndns.server.factories.dynamics;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.SquareArgument;
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

public class SquareGeneratedArgumentsDynamicTests {

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
        Random random = new Random();

        return IntStream.iterate(0, (n) -> n + 2).limit(100).mapToObj((_) -> {
            var number = random.nextInt(25000, 50000);
            var expected = Math.sqrt(number);
            return new SquareArgument(number, expected);
        });


    }
}
