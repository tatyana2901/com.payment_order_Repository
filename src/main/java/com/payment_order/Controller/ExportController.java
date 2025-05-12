package com.payment_order.Controller;

import com.payment_order.DTO.ExportDTO;
import com.payment_order.Service.ExportReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExportController {

    @Autowired
    ExportReportService exportReportService;


    @PostMapping("/save")
    public String saveToFile(RedirectAttributes redirectAttributes) {
        ExportDTO exportDTO = exportReportService.exportGeneralPayments();
        redirectAttributes.addFlashAttribute("exportDTO", exportDTO.getExportResult()); //добавляем новые атрибуты при переходе на главную страницу,чтобы не перезаполнять список платежей
        return "redirect:/";
    }

    @PostMapping("/savepurp")
    public String saveByPurp(RedirectAttributes redirectAttributes) {
        ExportDTO exportDTO = exportReportService.exportPaymentsByPurpose();
        redirectAttributes.addFlashAttribute("purpres", exportDTO.getExportResult());
        return "redirect:/report_by_purpose.html";
    }

    @PostMapping("/saverecip")
    public String saveByRecip(RedirectAttributes redirectAttributes) {
        ExportDTO exportDTO = exportReportService.exportPaymentsByRecipient();
        redirectAttributes.addFlashAttribute("recres", exportDTO.getExportResult());
        return "redirect:/report_by_recipient.html";
    }


}
