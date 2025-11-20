package com.jsharper.dyndns.server.providers;

import com.jsharper.dyndns.server.arguments.DivisionArgument;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class DivisionArgumentsProvider  implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        /*4, 2, 2
        8, 2, 4
        16, 2, 8
        16, 4, 4*/
        return Stream.of(
            Arguments.of(new DivisionArgument(4,2,2)),
                Arguments.of(       new DivisionArgument(8,2,4)),
                Arguments.of(    new DivisionArgument(16,2,8)),
                Arguments.of(     new DivisionArgument(16,4,4))

        );
        //return ArgumentsProvider.super.provideArguments(parameters, context);
    }
}
