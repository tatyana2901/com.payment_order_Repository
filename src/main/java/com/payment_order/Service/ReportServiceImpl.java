package com.payment_order.Service;

import com.payment_order.DTO.ReportDTO;
import com.payment_order.Entity.Payment;
import com.payment_order.Entity.Purpose;
import com.payment_order.Entity.SumByPurpose;
import com.payment_order.Entity.SumByRecip;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ReportDTO reportDTO;

    @Override
    public List<SumByRecip> totalSumByRecipient() {
        return paymentRepository.totalSumByRecipient();
    }

    @Override
    public List<SumByPurpose> totalSumByPurpose() {
        return paymentRepository.totalSumByPurpose();
    }

    @Override
    public List<Object> getSumByPurposeChartData() {
        return totalSumByPurpose().stream()
                .map((Function<SumByPurpose, Object>) sumByPurpose -> List.of(sumByPurpose.getPurpose(), sumByPurpose.getTotal()))
                .toList();
    }

    @Override
    public List<Object> getSumByRecipientChartData() {
        return totalSumByRecipient().stream()
                .map((Function<SumByRecip, Object>) sumByRecip -> List.of(sumByRecip.getRecipient(), sumByRecip.getTotal()))
                .toList();
    }

    public Map<String, Double> getSumByRecip(List<Payment> pays) {
        return pays.stream()
                .collect(Collectors.toMap((Payment::getRecipient), (Payment::getSum), Double::sum));
    }

    public Map<Purpose, Double> getSumByPurp(List<Payment> pays) {
        return pays.stream()
                .collect(Collectors.toMap((Payment::getPurpose), (Payment::getSum), Double::sum));

    }

    @Override
    public ReportDTO exportPaymentsByPurpose() {
        String fileName = reportDTO.getPurposeReportFile();
        List<String> lines = getSumByPurp(paymentRepository.findAll()).entrySet().stream()
                .map(enumDoubleEntry -> enumDoubleEntry.getKey().toString() + ";" + enumDoubleEntry.getValue().toString())
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

    @Override
    public ReportDTO exportPaymentsByRecipient() {
        String fileName = reportDTO.getRecipientReportFile();
        List<String> lines = getSumByRecip(paymentRepository.findAll()).entrySet()
                .stream()
                .map(x -> x.getKey() + ";" + x.getValue().toString())
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
