package com.jsharper.dyndns.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Supplier;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorValueSourceTest {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @DisplayName("divide 4/2 = 2")
    @ParameterizedTest
    @ValueSource(ints = {4, 4, 4})
    public void testDivide_FourDivideTwo_ExpectedTwo(int dividend) {
        //Arrange
        int divisor = 2;
        int expected = 2;
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
    @ValueSource(ints = {4, 4, 4})
    public void testMultiply_FourDivideTwo_ExpectedEight(int multiplier) {
        //Arrange
        int multiplicand = 2;
        int expected = 8;
        //Parameters

        String failureMessage = format("multiply could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);
        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("sum SummandOne+SummandTwo = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ValueSource(ints = {4, 4, 4})
    public void testSum_FourDivideTwo_ExpectedSix(int summandOne) {
        //Arrange
        int summandTwo = 2;
        int expected = 6;
        //Parameters

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("subtract Minuend - Subtrahend = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ValueSource(ints = {10, 10, 10})
    public void testSubtract_FourDivideTwo_ExpectedTwo(int minuend) {
        //Arrange
        int subtrahend = 2;
        int expected = 8;
        //Parameter
        String failureMessage = format("subtract could NOT procedure minuend %d and subtrahend %d and expected %d", minuend, subtrahend, expected);


        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, () -> failureMessage);
    }


    @DisplayName("square (Number) = Expected")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @ValueSource(ints = {16, 16, 16})
    public void testSquare_ResultSquareFromTen_ExpectedThree(double number) {
        //Arrange
        double expected = 4;
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
