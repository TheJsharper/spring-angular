package com.jsharper.dyndns.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorParametersTest {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @DisplayName("divide 4/2 = 2")
    @ParameterizedTest
    @MethodSource("divideInputParameters")
    public void testDivide_FourDivideTwo_ExpectedTwo(int dividend, int divisor, int expected) {
        //Arrange
        // Parameters

        String failureMessage = format("divide could NOT procedure dividend %d and divisor %d and expected %d", dividend, divisor, expected);

        //Act

        int result = calculator.divide(dividend, divisor);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

    private static Stream<Arguments> divideInputParameters() {
        return Stream.of(
                Arguments.of(4, 2, 2),
                Arguments.of(8, 2, 4)
        );
    }

    @DisplayName("divide 4/0 = IllegalArgumentException")
    @ParameterizedTest
    @MethodSource("divideInputParametersWithMessage")
    public void testDivideFourDivideZero_ExpectedIllegalException(int dividend, int divisor, String expectedMessage) {

        //Arrange
        //Parameters

        //ACT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(dividend, divisor));

        //Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> divideInputParametersWithMessage() {
        return Stream.of(
                Arguments.of(4, 0, "Divisor should NOT be ===> 0")
        );
    }

    @DisplayName("multiply 4*2 = 8")
    @ParameterizedTest
    @MethodSource("multiplyInputParameters")
    public void testMultiply_FourDivideTwo_ExpectedEight(int multiplier, int multiplicand, int expected) {
        //Arrange
        //Parameters

        String failureMessage = format("multiply could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);
        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

    private static Stream<Arguments> multiplyInputParameters() {
        return Stream.of(
                Arguments.of(4, 2, 8),
                Arguments.of(4, 4, 16),
                Arguments.of(5, 5, 25)
        );
    }

    @DisplayName("sum 4+2 = 6")
    @ParameterizedTest
    @MethodSource("sumInputParameters")
    public void testSum_FourDivideTwo_ExpectedSix(int summandOne, int summandTwo, int expected) {
        //Arrange
        //Parameters

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

    private static Stream<Arguments> sumInputParameters() {
        return Stream.of(
                Arguments.of(4, 2, 6),
                Arguments.of(4, 4, 8),
                Arguments.of(10, 2, 12),
                Arguments.of(25, 25, 50)
        );
    }

    @DisplayName("subtract 4 -2 = 2")
    @ParameterizedTest
    @MethodSource("subtractInputParameters")
    public void testSubtract_FourDivideTwo_ExpectedTwo(int minuend, int subtrahend, int expected) {
        //Arrange
        //Parameter
        String failureMessage = format("subtract could NOT procedure minuend %d and subtrahend %d and expected %d", minuend, subtrahend, expected);


        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

    private static Stream<Arguments> subtractInputParameters() {
        return Stream.of(
                Arguments.of(4, 2, 2),
                Arguments.of(4, 4, 0),
                Arguments.of(10, 2, 8),
                Arguments.of(25, 25, 0)
        );
    }


    @DisplayName("square (9) = 3")
    @ParameterizedTest
    @MethodSource("squareInputParameters")
    public void testSquare_ResultSquareFromTen_ExpectedThree(double number, double expected) {
        //Arrange
        // Parameters

        String failureMessage = format("square could NOT procedure number %f expected %f", number, expected);

        //Act

        double result = calculator.square(number);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }

    private static Stream<Arguments> squareInputParameters() {
        return Stream.of(
                Arguments.of(9, 3),
                Arguments.of(16, 4),
                Arguments.of(21, 4.58257569495584),
                Arguments.of(32, 5.656854249492381)
        );
    }

    @DisplayName("square (-1) = Exception")
    @ParameterizedTest
    @MethodSource("squareInputParametersWithMessage")
    public void testSquare_ResultSquareFromMinusOne_ExpectedException(double number, String expected) {
        //Arrange
        // Parameters
        String failureMessage = format("square could NOT procedure number %f expected %s", number, expected);

        Executable executable = () -> calculator.square(number);

        Supplier<String> message = () -> failureMessage;


        //Act

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable, message);

        //Assert
        assertEquals(expected, exception.getMessage(), () -> failureMessage);
    }

    private static Stream<Arguments> squareInputParametersWithMessage() {
        double number = -1d;
        return Stream.of(
                Arguments.of(-1d, format("calculation square need positive number %f", number))
        );
    }


}
