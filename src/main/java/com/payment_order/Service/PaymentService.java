package com.payment_order.Service;

import com.payment_order.DTO.PaymentDTO;
import com.payment_order.Entity.Payment;

import java.util.List;

public interface PaymentService {
    public List<PaymentDTO> getAllPays();

    public void addNewPay(PaymentDTO paymentDTO);

    public void deletePay(int id);

    public void savePays(List<Payment> list);

    public List<String> generateGeneralPayments();


}

