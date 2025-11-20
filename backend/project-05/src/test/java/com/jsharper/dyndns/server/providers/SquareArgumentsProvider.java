package com.jsharper.dyndns.server.providers;

import com.jsharper.dyndns.server.arguments.SquareArgument;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class SquareArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {

        return Stream.of(
          Arguments.of( new SquareArgument(9, 3)),
          Arguments.of( new SquareArgument(16, 4)),
          Arguments.of( new SquareArgument(21, 4.58257569495584)),
          Arguments.of( new SquareArgument(32, 5.656854249492381))
        );
    }
}
