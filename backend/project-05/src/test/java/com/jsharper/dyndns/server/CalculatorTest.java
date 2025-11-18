package com.jsharper.dyndns.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static  java.lang.String.*;

public class CalculatorTest {

    private Calculator calculator;
    @BeforeEach
    public  void setup(){
        calculator = new Calculator();
    }
    @DisplayName("divide 4/2 = 2")
    @Test
    public void testDivide_FourDivideTwo_ExpectedTwo(){
        //Arrange
        int dividend = 4;

        int divisor = 2;

        int expected = 2;

        String failureMessage = format("divide could NOT procedure dividend %d and divisor %d and expected %d", dividend, divisor, expected);

        //Act

        int result = calculator.divide(dividend, divisor);
        //Assert
        assertEquals(expected, result, ()-> failureMessage);
    }

    @DisplayName("divide 4/0 = IllegalArgumentException")
    @Test
    public void testDivideFourDivideZero_ExpectedIllegalException(){

        //Arrange
        int dividend = 4;

        int divisor = 0;

       String expectedMessage= format("Divisor should NOT be ===> %s", divisor);

        //ACT
       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->calculator.divide(dividend, divisor));

       //Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @DisplayName("multiply 4*2 = 8")
    @Test
    public void testMultiply_FourDivideTwo_ExpectedEight(){
        //Arrange
        int multiplier = 4;

        int multiplicand = 2;

        int expected = 8;

        String failureMessage = format("multiply could NOT procedure multiplier %d and  multiplicand %d and expected %d", multiplier, multiplicand, expected);
        //Act

        int result = calculator.multiply(multiplier, multiplicand);
        //Assert
        assertEquals(expected, result, ()-> failureMessage);
    }

    @DisplayName("sum 4+2 = 6")
    @Test
    public void testSum_FourDivideTwo_ExpectedSix(){
        //Arrange
        int summandOne = 4;

        int summandTwo = 2;

        int expected = 6;

        String failureMessage = format("sum could NOT procedure summandOne %d and  summandTwo %d and expected %d", summandOne, summandTwo, expected);

        //Act

        int result = calculator.sum(summandOne, summandTwo);
        //Assert
        assertEquals(expected, result, ()-> failureMessage);
    }

    @DisplayName("subtract 4 -2 = 2")
    @Test
    public void testSubtract_FourDivideTwo_ExpectedTwo(){
        //Arrange
        int minuend = 4;

        int subtrahend = 2;

        int expected = 2;

        String failureMessage = format("subtract could NOT procedure minuend %d and subtrahend %d and expected %d", minuend, subtrahend, expected);


        //Act

        int result = calculator.subtract(minuend, subtrahend);
        //Assert
        assertEquals(expected, result, ()-> failureMessage);
    }



    @DisplayName("square (9) = 3")
    @Test
    public void testSquare_ResultSquareFromTen_ExpectedThree(){
        //Arrange
       double number = 9d;

       double expected = 3d;

       String failureMessage = format("square could NOT procedure number %f expected %f", number, expected);

        // String message ="""";
        //Act

        double result = calculator.square(number);
        //Assert
        assertEquals(expected, result, ()-> failureMessage);
    }


    @DisplayName("square (-1) = Exception")
    @Test
    public void testSquare_ResultSquareFromMinusOne_ExpectedException(){
        //Arrange
        double number = -1;

        String expected = format("calculation square need positive number %f", number);

        String failureMessage = format("square could NOT procedure number %f expected %s", number, expected);

        Executable executable = ()-> calculator.square(number);

        Supplier<String> message = ()-> failureMessage;


        //Act

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable,message );

        //Assert
        assertEquals(expected, exception.getMessage(), ()-> failureMessage);
    }



}
