package com.example.springtunes.models;

public class Country {
    private String name;
    private String customerCount;

    public Country(String name, String customerCount) {
        this.name = name;
        this.customerCount = customerCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(String customerCount) {
        this.customerCount = customerCount;
    }
}
