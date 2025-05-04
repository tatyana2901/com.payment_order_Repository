package com.payment_order.Service;
import com.payment_order.Entity.Payment;
import com.payment_order.Entity.Purpose;
import com.payment_order.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    public Map<String, Double> getSumByRecip(List<Payment> pays) {
        return pays.stream()
                .collect(Collectors.toMap((Payment::getRecipient), (Payment::getSum), Double::sum
                ));
    }

    public Map<Purpose, Double> getSumByPurp(List<Payment> pays) {
        return
                pays.stream()
                        .collect(Collectors.toMap((Payment::getPurpose), (Payment::getSum), Double::sum
                        ));

    }

    public void save(String typeOfReport, List<Payment> pays) throws IOException {
        String fname = null;
        List<String> lines = null;
        switch (typeOfReport) {
            case "general":
                fname = "saved_payments.txt";
                lines = pays.stream().map(payment -> payment.getNumber() + ";" + payment.getDate() + ";" + payment.getSum() + ";" + payment.getRecipient() + ";" + payment.getPurpose()).toList();
                break;
            case "by_purpose":
                fname = "payments_by_purpose.txt";
                lines = getSumByPurp(pays).entrySet().stream().map(enumDoubleEntry -> enumDoubleEntry.getKey().toString() + ";" + enumDoubleEntry.getValue().toString()).toList();
                break;
            case "by_recip":
                fname = "payments_by_recipient.txt";
                lines = getSumByRecip(pays).entrySet()
                        .stream()
                        .map(x -> x.getKey() + ";" + x.getValue().toString())
                        .toList();
                break;
        }
        Path file = Paths.get(fname);


            Files.write(file, lines, StandardCharsets.UTF_8);


    }



}
