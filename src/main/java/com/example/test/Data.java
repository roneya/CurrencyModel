package com.example.test;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="cash")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private LocalDate localDate;

    private double price;


    private String currency; //3 chars

    public Data() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
