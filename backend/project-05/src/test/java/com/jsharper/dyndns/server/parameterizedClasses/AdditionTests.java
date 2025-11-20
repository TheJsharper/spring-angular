package com.jsharper.dyndns.server.parameterizedClasses;

import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParameterizedClass(name = "[{index}] {arguments}")
@DisplayName("sum SummandOne+SummandTwo = Expected")
@CsvSource(textBlock = """
                SummandOne, SummandTwo, Expected
                4, 2, 6
                4, 4, 8
                10, 2, 12
                25, 25, 50
            """, useHeadersInDisplayName = true)

public class AdditionTests {
    private Calculator calculator;
    @Parameter(0)
    int summandOne;
    @Parameter(1)
    int summandTwo;
    @Parameter(2)
    int expected;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testSum_FourDivideTwo_ExpectedSix() {
        //Arrange
        //Parameters

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }
}
