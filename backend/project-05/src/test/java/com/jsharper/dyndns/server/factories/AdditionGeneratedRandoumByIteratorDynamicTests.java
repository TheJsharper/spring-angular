package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.AdditionArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class AdditionGeneratedRandoumByIteratorDynamicTests {

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

        // Generates display names like: input:5, input:37, input:85, etc.
        Function<AdditionArgument, String> displayNameGenerator = (argument) -> format("test dynamic SummandOne(%d) + SummandTwo(%d) = Expected(%d) ", argument.summandOne(), argument.summandTwo(), argument.expected());

        // Executes tests based on the current input value.
        ThrowingConsumer<AdditionArgument> testExecutor = (argument) -> assertEquals(argument.expected(), calculator.sum(argument.summandOne(), argument.summandTwo()));


        return DynamicTest.stream(getArguments(), displayNameGenerator, testExecutor);

    }


    private Iterator<AdditionArgument> getArguments() {


        Iterator<AdditionArgument> inputGenerator = new Iterator<>() {

            final Random random = new Random();
            AdditionArgument current;

            @Override
            public boolean hasNext() {
                var summandOne = random.nextInt(100, 10000);
                var summandTwo = random.nextInt(100, 10000);
                current = new AdditionArgument(summandOne, summandTwo, summandOne + summandTwo);
                return summandOne % 7 != 0;
            }

            @Override
            public AdditionArgument next() {
                return current;
            }
        };

        // Generates display names like: input:5, input:37, input:85, etc.
        Function<AdditionArgument, String> displayNameGenerator = (argument) -> format("test dynamic SummandOne(%d) + SummandTwo(%d) = Expected(%d) ", argument.summandOne(), argument.summandTwo(), argument.expected());

        // Executes tests based on the current input value.
        ThrowingConsumer<AdditionArgument> testExecutor = (argument) -> assertEquals(argument.expected(), calculator.sum(argument.summandOne(), argument.summandTwo()));


        DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);


        return inputGenerator;
    }
}
