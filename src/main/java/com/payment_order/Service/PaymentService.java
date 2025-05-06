package com.payment_order.Service;

import com.payment_order.Entity.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    public List<Payment> getAllPays();

    public void addNewPay(Payment payment);

    public void deletePay(int id);

    public void savePays(List<Payment> list);

    public void exportGeneralPayments(List<Payment> pays) throws IOException;


}

