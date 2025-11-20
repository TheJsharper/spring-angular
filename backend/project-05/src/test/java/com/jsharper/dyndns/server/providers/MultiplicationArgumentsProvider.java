package com.jsharper.dyndns.server.providers;

import com.jsharper.dyndns.server.arguments.MultiplicationArgument;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class MultiplicationArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {

        return Stream.of(
                Arguments.of(new MultiplicationArgument(4, 2, 8)),
                Arguments.of(new MultiplicationArgument(4, 4, 16)),
                Arguments.of(new MultiplicationArgument(5, 5, 25)),
                Arguments.of(new MultiplicationArgument(2, 5, 10)),
                Arguments.of(new MultiplicationArgument(9, 9, 81))
        );
    }
}
