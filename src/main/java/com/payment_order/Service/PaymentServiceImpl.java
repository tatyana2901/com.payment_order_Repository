package com.payment_order.Service;

import com.payment_order.DTO.PaymentDTO;
import com.payment_order.DTO.ReportDTO;
import com.payment_order.Entity.Payment;
import com.payment_order.Repository.PaymentRepository;
import com.payment_order.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ReportDTO reportDTO;

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
        String fileName = reportDTO.getGeneralReportFile();
        List<String> lines = paymentRepository.findAll().stream()
                .map(payment -> payment.getNumber() + ";" + payment.getDate() + ";" + payment.getSum() + ";" + payment.getRecipient() + ";" + payment.getPurpose())
                .toList();
        try {
            FileUtils.saveToFile(fileName, lines);
            reportDTO.setExportResult("Выгрузка успешно завершена");
        } catch (IOException e) {
            reportDTO.setExportResult(e.getMessage());
            return reportDTO;
        }
        return reportDTO;

    }


}
