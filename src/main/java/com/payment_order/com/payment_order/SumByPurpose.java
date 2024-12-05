package com.payment_order.com.payment_order;

public class SumByPurpose {
    private Purpose purpose;
    private Double total;

    public SumByPurpose(Purpose purpose, Double total) {
        this.purpose = purpose;
        this.total = total;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SumByPurpose{" +
                "purpose=" + purpose +
                ", total=" + total +
                '}';
    }
}
