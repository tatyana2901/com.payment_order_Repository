package com.payment_order.Service;

import com.payment_order.Entity.Payment;
import com.payment_order.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPays() {
        return paymentRepository.findAll();
    }

    @Override
    public void addNewPay(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void deletePay(int id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public void savePays(List<Payment> list) {
        paymentRepository.saveAll(list);
    }

    @Override
    public void exportGeneralPayments(List<Payment> pays) throws IOException {

        String fname = "saved_payments.txt";
        List<String> lines = pays.stream()
                .map(payment -> payment.getNumber() + ";" + payment.getDate() + ";" + payment.getSum() + ";" + payment.getRecipient() + ";" + payment.getPurpose())
                .toList();

        FileUtils.saveToFile(fname, lines);

    }


}
