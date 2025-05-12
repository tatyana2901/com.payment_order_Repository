package com.payment_order.Service;

import com.payment_order.Entity.SumByPurpose;
import com.payment_order.Entity.SumByRecip;
import com.payment_order.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;


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

    @Override
    public List<String> generatePaymentsByRecipientReport() {
        List<String> lines = totalSumByRecipient().stream()
                .map(x -> x.getRecipient() + ";" + x.getTotal())
                .toList();
        return lines;
    }

    @Override
    public List<String> generatePaymentsByPurposeReport() {
        List<String> lines = totalSumByPurpose().stream()
                .map(x -> x.getPurpose() + ";" + x.getTotal())
                .toList();
        return lines;
    }


}
