package com.payment_order.Service;

import com.payment_order.DTO.ExportDTO;

public interface ExportReportService {

    public ExportDTO exportGeneralPayments();

    public ExportDTO exportPaymentsByPurpose();

    public ExportDTO exportPaymentsByRecipient();
}
