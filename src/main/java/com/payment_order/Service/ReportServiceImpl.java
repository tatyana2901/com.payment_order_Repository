package com.payment_order.Service;

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
                .collect(Collectors.toMap((Payment::getRecipient), (Payment::getSum), Double::sum
                ));
    }

    public Map<Purpose, Double> getSumByPurp(List<Payment> pays) {
        return
                pays.stream()
                        .collect(Collectors.toMap((Payment::getPurpose), (Payment::getSum), Double::sum
                        ));

    }

    @Override
    public void exportPaymentsByPurpose(List<Payment> pays) throws IOException {
        String fname = "payments_by_purpose.txt";
        List<String> lines = getSumByPurp(pays).entrySet().stream().map(enumDoubleEntry -> enumDoubleEntry.getKey().toString() + ";" + enumDoubleEntry.getValue().toString()).toList();
        FileUtils.saveToFile(fname, lines); //обработать исключения
    }

    @Override
    public void exportPaymentsByRecipient(List<Payment> pays) throws IOException {
        String fname = "payments_by_recipient.txt";
        List<String> lines = getSumByRecip(pays).entrySet()
                .stream()
                .map(x -> x.getKey() + ";" + x.getValue().toString())
                .toList();
        FileUtils.saveToFile(fname, lines); //обработать исключения
    }


}
