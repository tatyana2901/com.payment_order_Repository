package com.payment_order.DTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class ReportDTO {

    private String exportResult;
    @Value("${report.general.file}")
    private String generalReportFile;

    @Value("${report.recipient.file}")
    private String recipientReportFile;

    @Value("${report.purpose.file}")
    private String purposeReportFile;

    public ReportDTO() {
    }

    public ReportDTO(String exportResult) {
        this.exportResult = exportResult;
    }

    public String getExportResult() {
        return exportResult;
    }

    public void setExportResult(String exportResult) {
        this.exportResult = exportResult;
    }

    public String getGeneralReportFile() {
        return generalReportFile;
    }

    public void setGeneralReportFile(String generalReportFile) {
        this.generalReportFile = generalReportFile;
    }

    public String getRecipientReportFile() {
        return recipientReportFile;
    }

    public void setRecipientReportFile(String recipientReportFile) {
        this.recipientReportFile = recipientReportFile;
    }

    public String getPurposeReportFile() {
        return purposeReportFile;
    }

    public void setPurposeReportFile(String purposeReportFile) {
        this.purposeReportFile = purposeReportFile;
    }
}
