package com.payment_order.DTO;


public class ReportDTO {

    private final String exportResult;

    public ReportDTO(String exportResult) {
        this.exportResult = exportResult;
    }

    public String getExportResult() {
        return exportResult;
    }


}
