package com.jsharper.dyndns.server.entities.fetch;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.util.StringJoiner;

@Entity
public class PhoneFetch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_fetch_id")
    private CustomerFetch customerFetch;
    @Transient
    private Long customerFetchId;

    @Contract(pure = true)
    public PhoneFetch(Long id, String number, String type, CustomerFetch customerFetch) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.customerFetch = customerFetch;
    }

    public PhoneFetch(Long id, String number, String type) {
        this.id = id;
        this.number = number;
        this.type = type;
    }

    public PhoneFetch(String number,String type) {
        this.type = type;
        this.number = number;
    }

    public PhoneFetch(Long id, String number, String type, Long customerFetchId) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.customerFetchId = customerFetchId;
    }

    @Contract(pure = true)
    public PhoneFetch() {
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

    public CustomerFetch getCustomerFetch() {
        return customerFetch;
    }

    public void setCustomerFetch(CustomerFetch customerFetch) {
        this.customerFetch = customerFetch;
    }

    public Long getCustomerFetchId() {
        return customerFetchId;
    }

    public void setCustomerFetchId(Long customerFetchId) {
        this.customerFetchId = customerFetchId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PhoneFetch.class.getSimpleName() + "[", "]")
                .add("customerFetchId=" + customerFetchId)
                .add("type='" + type + "'")
                .add("number='" + number + "'")
                .add("id=" + id)
                .toString();
    }
}
