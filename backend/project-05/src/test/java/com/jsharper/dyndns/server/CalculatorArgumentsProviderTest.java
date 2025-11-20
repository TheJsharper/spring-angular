package com.jsharper.dyndns.server;

import com.jsharper.dyndns.server.arguments.*;
import com.jsharper.dyndns.server.providers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Supplier;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorArgumentsProviderTest {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @DisplayName("divide Dividend/Divisor = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ArgumentsSource(DivisionArgumentsProvider.class)
    public void testDivide_FourDivideTwo_ExpectedTwo(DivisionArgument divisionArgument) {
        //Arrange
        int dividend = divisionArgument.dividend();
        int divisor = divisionArgument.divisor();
        int expected = divisionArgument.expected();
        // Parameters

        String failureMessage = format("divide could NOT procedure dividend %d and divisor %d and expected %d", dividend, divisor, expected);

        //Act

        int result = calculator.divide(dividend, divisor);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("divide 4/0 = IllegalArgumentException")
    @ParameterizedTest
    @CsvSource({
            "4, 0, 'Divisor should NOT be ===> 0'"
    })
    public void testDivideFourDivideZero_ExpectedIllegalException(int dividend, int divisor, String expectedMessage) {

        //Arrange
        //Parameters

        //ACT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(dividend, divisor));

        //Assert
        assertEquals(expectedMessage, exception.getMessage());
    }


    @DisplayName("multiply Multiplier*Multiplicand = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ArgumentsSource(MultiplicationArgumentsProvider.class)
    public void testMultiply_FourDivideTwo_ExpectedEight(MultiplicationArgument multiplicationArgument) {
        //Arrange
        int multiplier = multiplicationArgument.multiplier();
        int multiplicand = multiplicationArgument.multiplicand();
        int expected = multiplicationArgument.expected();
        //Parameters

        String failureMessage = format("multiply could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);
        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("sum SummandOne+SummandTwo = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ArgumentsSource(AdditionArgumentsProvider.class)
    public void testSum_FourDivideTwo_ExpectedSix(AdditionArgument additionArgument) {
        //Arrange
        int summandOne = additionArgument.summandOne();
        int summandTwo = additionArgument.summandTwo();
        int expected = additionArgument.expected();
        //Parameters

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("subtract Minuend - Subtrahend = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ArgumentsSource(SubtractionArgumentsProvider.class)
    public void testSubtract_FourDivideTwo_ExpectedTwo(SubtractionArgument subtractionArgument) {
        //Arrange
        int minuend = subtractionArgument.minuend();
        int subtrahend = subtractionArgument.subtrahend();
        int expected = subtractionArgument.expected();
        //Parameter
        String failureMessage = format("subtract could NOT procedure minuend %d and subtrahend %d and expected %d", minuend, subtrahend, expected);


        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("square (Number) = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ArgumentsSource(SquareArgumentsProvider.class)
    public void testSquare_ResultSquareFromTen_ExpectedThree(SquareArgument squareArgument) {
        //Arrange
        double number = squareArgument.number();
        double expected = squareArgument.expected();
        // Parameters

        String failureMessage = format("square could NOT procedure number %f expected %f", number, expected);

        //Act

        double result = calculator.square(number);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("square (-1) = Exception")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @CsvSource(textBlock = """
                Number; Expected
                -1d; 'calculation square need positive number -1,000000'
            """, delimiterString = ";", useHeadersInDisplayName = true)
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


}
