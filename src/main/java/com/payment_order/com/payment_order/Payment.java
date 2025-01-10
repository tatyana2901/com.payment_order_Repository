package com.payment_order.com.payment_order;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "payment_order")
public class Payment {
    // public static int count = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String recipient;
    @Column
    private LocalDate date;
    @Column
    private Double sum;

    @Column
    @Enumerated(EnumType.STRING)
    private Purpose purpose;
    @Column
    private int number;

    public Payment() {
    }


    public Payment(int id, String recipient, LocalDate date, Double sum, Purpose purpose, int number) {
        this.id = id;
        this.recipient = recipient;
        this.date = date;
        this.sum = sum;
        this.purpose = purpose;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "recipient='" + recipient + '\'' +
                ", date=" + date +
                ", sum=" + sum +
                ", purpose=" + purpose +
                ", number=" + number +
                ", id=" + id +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getSum() {
        return sum;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}


