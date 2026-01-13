package com.jsharper.dyndns.server.entities.cascade;

import jakarta.persistence.*;

@Entity
public class PhoneCascade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String type;
    @ManyToOne
    @JoinColumn(name = "customer_cascade_id")
    private CustomerCascade customerCascade;
    @Transient
    private Long customerCascadeId;

    public PhoneCascade(Long id, String number, String type, Long customerCascadeId) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.customerCascadeId = customerCascadeId;
    }

    public PhoneCascade(Long id, String number, String type, CustomerCascade customerCascade) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.customerCascade = customerCascade;
    }

    public PhoneCascade(Long id, String number, String type) {
        this.id = id;
        this.number = number;
        this.type = type;
    }

    public PhoneCascade(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public PhoneCascade(String number, String type, CustomerCascade customer) {
        this.number = number;
        this.type = type;
        this.customerCascade = customer;
    }

    public PhoneCascade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomerCascade getCustomerCascade() {
        return customerCascade;
    }

    public void setCustomerCascade(CustomerCascade customerCascade) {
        this.customerCascade = customerCascade;
    }

    public Long getCustomerCascadeId() {
        return customerCascadeId;
    }

    public void setCustomerCascadeId(Long customerCascadeId) {
        this.customerCascadeId = customerCascadeId;
    }

    @Override
    public String toString() {
        return "PhoneCascade{" +
                "type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", id=" + id +
                '}';
    }
}
