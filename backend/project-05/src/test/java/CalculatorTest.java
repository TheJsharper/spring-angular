import com.jsharper.dyndns.server.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;
    @BeforeEach
    public  void setup(){
        calculator = new Calculator();
    }
    @Test
    public void testDivide_FourDivideTwo_ExpectedTwo(){
        //Arrange
        int dividend = 4;
        int divisor = 2;

        //Act

        int result = calculator.divide(dividend, divisor);
        int expected = 2;
        //Assert
        assertEquals(expected, result);
    }


    @Test
    public void testDivideFourDivideZero_ExpectedIllegalException(){

        //Arrange
        int dividend = 4;
        int divisor = 0;
       String expectedMessage= String.format("Divisor should NOT be ===> %s", divisor);

        //ACT
       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->calculator.divide(dividend, divisor));

       //Assert
        assertEquals(expectedMessage, exception.getMessage());
    }





}
