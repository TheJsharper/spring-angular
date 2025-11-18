package com.jsharper.dyndns.server;
import static  java.lang.String.*;
import static  java.lang.Math.*;
public class Calculator {

    public int divide( int dividend, int divisor ){

        if(divisor == 0) throw new IllegalArgumentException(format("Divisor should NOT be ===> %s", divisor));

        return dividend/divisor;
    }

    public int multiply(int multiplier,  int multiplicand ){
        return multiplier * multiplicand;
    }

    public int sum(int summandOne, int summandTwo){
        return  summandOne + summandTwo;
    }

    public int subtract(int minuend, int subtrahend){
        return minuend - subtrahend;
    }

    public double square(double number){
        if(number < 0)
            throw new IllegalArgumentException(format("calculation square need positive number %f", number));
        return sqrt(number);
    }
}
