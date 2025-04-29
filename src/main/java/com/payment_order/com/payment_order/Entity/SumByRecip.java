package com.payment_order.com.payment_order.Entity;

public class SumByRecip {
    private String recipient;
   // private Double sum;
    private Double total;


    public String getRecipient() {
        return recipient;
    }

    @Override
    public String toString() {
        return "SumByRecip{" +
                "recipient='" + recipient + '\'' +
            //
                ", total=" + total +
                '}';
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

 /*   public Double getSum() {
        return sum;
    }*/

  /*  public void setSum(Double sum) {
        this.sum = sum;
    }*/

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public SumByRecip(String recipient, Double total) {
        this.recipient = recipient;
      //  this.sum = sum;
        this.total = total;
    }
}
