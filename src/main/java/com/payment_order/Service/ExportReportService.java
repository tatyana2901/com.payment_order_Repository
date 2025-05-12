package com.payment_order.Service;

import com.payment_order.DTO.ReportDTO;

public interface ExportReportService {

    public ReportDTO exportGeneralPayments();

    public ReportDTO exportPaymentsByPurpose();

    public ReportDTO exportPaymentsByRecipient();
}
