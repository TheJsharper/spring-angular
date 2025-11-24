package com.jsharper.dyndns.server.factories;

import com.jsharper.dyndns.server.Calculator;
import com.jsharper.dyndns.server.arguments.MultiplicationArgument;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.NamedExecutable;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationGeneratedFromMethoddWithNamesDynamicTests {
    private final Calculator calculator = new Calculator();


    @TestFactory
    Stream<DynamicTest> dynamicTestsMultiplicationFromStream() {


        var inputStream = getInitMultiplicationTestCases().map((argument) -> new GeneratedFromMethodWithNames(argument, new Calculator()));

        return DynamicTest.stream(inputStream);
    }

    private Stream<MultiplicationArgument> getInitMultiplicationTestCases() {
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

record GeneratedFromMethodWithNames(MultiplicationArgument argument, Calculator calculator) implements NamedExecutable {

    @org.jetbrains.annotations.NotNull
    @Override
    public String getName() {
        return format("test dynamic Multiplier(%d) * Multiplicand(%d) = Expected(%d) ", argument.multiplier(), argument.multiplicand(), argument.expected());
    }

    @Override
    public void execute() {
        assertEquals(argument.expected(), calculator.multiply(argument.multiplier(), argument.multiplicand()));
    }
}