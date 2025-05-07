package com.payment_order.DTO;

import com.payment_order.Entity.Purpose;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PaymentDTO {
    private int id;
    private String recipient;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private Double sum;

    private Purpose purpose;

    private int number;

    public PaymentDTO() {
    }


    public PaymentDTO(int id, String recipient, LocalDate date, Double sum, Purpose purpose, int number) {
        this.id = id;
        this.recipient = recipient;
        this.date = date;
        this.sum = sum;
        this.purpose = purpose;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "recipient='" + recipient + '\'' +
                ", date=" + date +
                ", sum=" + sum +
                ", purpose=" + purpose +
                ", number=" + number +
                '}';
    }
}
