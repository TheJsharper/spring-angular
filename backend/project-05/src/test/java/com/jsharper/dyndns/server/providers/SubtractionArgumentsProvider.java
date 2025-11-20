package com.jsharper.dyndns.server.providers;

import com.jsharper.dyndns.server.arguments.SubtractionArgument;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class SubtractionArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {

        return Stream.of(
                Arguments.of(new SubtractionArgument(4, 2, 2)),
                Arguments.of(new SubtractionArgument(4, 4, 0)),
                Arguments.of(new SubtractionArgument(10, 2, 8)),
                Arguments.of(new SubtractionArgument(25, 5, 20)),
                Arguments.of(new SubtractionArgument(40, 2, 38)),
                Arguments.of(new SubtractionArgument(40, 20, 20)),
                Arguments.of(new SubtractionArgument(400, 200, 200))
        );
    }
}
