package com.payment_order.Service;

import com.payment_order.Entity.SumByPurpose;
import com.payment_order.Entity.SumByRecip;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReportService {

    public List<SumByRecip> totalSumByRecipient();

    public List<SumByPurpose> totalSumByPurpose();

    public List<Object> getSumByPurposeChartData();

    public List<Object> getSumByRecipientChartData();
}
