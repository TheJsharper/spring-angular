package com.jsharper.dyndns.server.entities.inheritance.payments;

import com.jsharper.dyndns.server.entities.inheritance.Payment;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("check")
public class Check extends Payment {
    private String checkNumber;

    public Check(String checkNumber, double amount) {
        this.checkNumber = checkNumber;
    }

    public Check() {
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }
}
