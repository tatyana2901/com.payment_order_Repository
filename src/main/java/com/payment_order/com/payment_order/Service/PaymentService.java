package com.payment_order.com.payment_order.Service;

import com.payment_order.com.payment_order.Entity.Payment;
import com.payment_order.com.payment_order.Entity.SumByPurpose;
import com.payment_order.com.payment_order.Entity.SumByRecip;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    public List<Payment> getAllPays();

    public void addNewPay(Payment payment);


    public List<SumByRecip> totalSumByRecipient();

    public List<SumByPurpose> totalSumByPurpose();

    public void deletePay(int id);

    public void savePays(List<Payment> list);

    public void save(String general, List<Payment> allPays) throws IOException;
}

