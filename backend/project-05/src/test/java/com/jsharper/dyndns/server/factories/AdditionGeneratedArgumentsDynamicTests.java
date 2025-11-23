package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.AdditionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class AdditionGeneratedArgumentsDynamicTests {

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
        Random random = new Random();

      return    IntStream.iterate(0, (n)-> n +2).limit(100).mapToObj( (_)->{
             var summandOne = random.nextInt();
             var summandTwo = random.nextInt();
            return  new AdditionArgument(summandOne, summandTwo, summandOne + summandTwo)
        ; });



    }
}
