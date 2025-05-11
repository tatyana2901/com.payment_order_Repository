package com.payment_order.Service;

import com.payment_order.DTO.PaymentDTO;
import com.payment_order.DTO.ReportDTO;
import com.payment_order.Entity.Payment;
import com.payment_order.Repository.PaymentRepository;
import com.payment_order.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${report.general.file}")
    private String generalReportFile;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;


    @Override
    public List<PaymentDTO> getAllPays() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> paymentMapper.toPaymentDTO(payment))
                .toList();
    }


    @Override
    public void addNewPay(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.toEntity(paymentDTO);
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
    public ReportDTO exportGeneralPayments() {
        try {
            return FileUtils.saveToFile(generalReportFile, generateGeneralPayments());
        } catch (IOException e) {
            return new ReportDTO(e.getMessage());
        }
    }

    public List<String> generateGeneralPayments() {
        List<String> lines = paymentRepository.findAll().stream()
                .map(payment -> payment.getNumber() + ";" + payment.getDate() + ";" + payment.getSum() + ";" + payment.getRecipient() + ";" + payment.getPurpose())
                .toList();
        return lines;
    }


}
