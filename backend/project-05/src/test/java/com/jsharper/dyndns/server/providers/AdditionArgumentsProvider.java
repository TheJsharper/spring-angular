package com.jsharper.dyndns.server.providers;

import com.jsharper.dyndns.server.arguments.AdditionArgument;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class AdditionArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {

        return Stream.of(
                Arguments.of(new AdditionArgument(4,2, 6)),
                Arguments.of(new AdditionArgument(4,4, 8)),
                Arguments.of(new AdditionArgument(10,2, 12)),
                Arguments.of(new AdditionArgument(25,25, 50)),
                Arguments.of(new AdditionArgument(50,10, 60)),
                Arguments.of(new AdditionArgument(100,50, 150))
        );
    }
}
