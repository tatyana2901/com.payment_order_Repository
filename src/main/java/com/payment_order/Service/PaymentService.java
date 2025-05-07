package com.payment_order.Service;

import com.payment_order.DTO.PaymentDTO;
import com.payment_order.DTO.ReportDTO;
import com.payment_order.Entity.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    public List<PaymentDTO> getAllPays();

    public void addNewPay(PaymentDTO paymentDTO);

    public void deletePay(int id);

    public void savePays(List<Payment> list);

    public ReportDTO exportGeneralPayments();


}

