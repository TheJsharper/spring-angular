package com.jsharper.dyndns.server.entities.inheritance.payments;

import com.jsharper.dyndns.server.entities.inheritance.Payment;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("credit_card")
public class CreditCard extends Payment {
    private String creditCard;

    public CreditCard(String creditCard, double amount) {
        this.setAmount(amount);
        this.creditCard = creditCard;

    }

    public CreditCard() {
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCard='" + creditCard + '\'' +
                "} " + super.toString();
    }
}
