package com.payment_order.com.payment_order;

import java.time.LocalDate;
import java.util.Date;

public class Payment {
    public static int count = 0;
    private String recipient;
    private LocalDate date;  ////????
    private Double sum;
    private Enum purpose;
    private int id;


    public Payment(String recipient, LocalDate date, Double sum, Enum purpose) {
        this.recipient = recipient;
        this.date = date;
        this.sum = sum;
        this.purpose = purpose;
        count++;
        id = count;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "recipient='" + recipient + '\'' +
                ", date=" + date +
                ", sum=" + sum +
                ", purpose=" + purpose +
                ", id=" + id +
                '}';
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

    public Enum getPurpose() {
        return purpose;
    }


}


