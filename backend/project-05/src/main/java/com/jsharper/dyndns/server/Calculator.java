package com.jsharper.dyndns.server;

public class Calculator {

    public int divide( int dividend, int divisor ){
        if(divisor == 0) throw new IllegalArgumentException(String.format("Divisor should NOT be ===> %s", divisor));
        return dividend/divisor;
    }
}
