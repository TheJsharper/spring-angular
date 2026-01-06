package com.jsharper.dyndns.server.entities.inheritance.people;

import com.jsharper.dyndns.server.entities.inheritance.Person;
import com.jsharper.dyndns.server.entities.inheritance.details.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class Employee extends Person {
    private String cardId;

    @Embedded
    private Address address;

    public Employee(String cardId, String firstName, String lastName, Address address) {
        this.cardId = cardId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
    }

    public Employee() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "cardId='" + cardId + '\'' +
                ", address=" + address +
                "} " + super.toString();
    }
}
