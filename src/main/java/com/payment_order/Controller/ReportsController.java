package com.payment_order.Controller;

import com.payment_order.Entity.SumByPurpose;
import com.payment_order.Entity.SumByRecip;
import com.payment_order.Service.ExportReportService;
import com.payment_order.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class ReportsController {

    @Autowired
    ReportService reportService;
    @Autowired
    ExportReportService exportReportService;

    @GetMapping("/report_by_recipient.html")
    public String getRecipientReport(Model model) {
        List<SumByRecip> report = reportService.totalSumByRecipient();
        List<Object> chartData = reportService.getSumByRecipientChartData();
        model.addAttribute("report", report);
        model.addAttribute("chartData", chartData);
        return "report_by_recipient";
    }

    @GetMapping("/report_by_purpose.html")
    public String getPurposeReport(Model model) {
        List<SumByPurpose> report = reportService.totalSumByPurpose();
        List<Object> chartData = reportService.getSumByPurposeChartData();
        model.addAttribute("report", report);
        model.addAttribute("chartData", chartData);
        return "report_by_purpose";
    }



}
