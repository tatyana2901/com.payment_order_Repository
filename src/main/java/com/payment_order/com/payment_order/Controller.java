package com.payment_order.com.payment_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    @Qualifier("paymentRepository")
    PaymentRepository pr;
    @Autowired
    @Qualifier("paymentService")
    PaymentService ps;

    @GetMapping("/")
    public String goToFirstView(Model model) {
        List<Payment> list = pr.findAll();
        model.addAttribute("tab_lines", list);
        return "first-view";
    }

    @GetMapping("/enter-new-pay.html")
    public String goToEnterForm() {
        return "enter-new-pay";
    }

    @GetMapping("/added")
    public String addPayment(String data, String recipient, String sum, String num, String purpose, Model model) {
        pr.save(ps.add(data, recipient, sum, num, purpose));

        return "redirect:/enter-new-pay.html";
    }

    @GetMapping("/report_by_recipient.html")
    public String getRecipientReport(Model model) {

        var report = pr.totalSumByRecipient();
         model.addAttribute("report", report);
        return "report_by_recipient";
    }

    @GetMapping("/report_by_purpose.html")
    public String getPurposeReport(Model model) {
        var report = pr.totalSumByPurpose();
        model.addAttribute("report", report);
        return "report_by_purpose";
    }


    @PostMapping("/save")
    public String saveToFile(Model model) {
        goToFirstView(model);
        try {
            ps.save("general", pr.findAll());
            model.addAttribute("result", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("result", e.getMessage());
        }

        return "first-view";
    }

    @PostMapping("/savepurp")
    public String saveByPurp(Model model) {
        getPurposeReport(model);
        try {
            ps.save("by_purpose", pr.findAll());
            model.addAttribute("purpres", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("purpres", e.getMessage());
        }
        return "report_by_purpose";
    }

    @PostMapping("/saverecip")
    public String saveByRecip(Model model) {
        getRecipientReport(model);
        try {
            ps.save("by_recip", pr.findAll());
            model.addAttribute("recres", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("recres", e.getMessage());
        }
        return "report_by_recipient";
    }

    @GetMapping("/deletePay")
    public String delete(int id) {
        pr.deleteById(id);
        /*System.out.println("РАБОТАЕТ КОНТРОЛЛЕР!!!");
        System.out.println(id);*/
        return "redirect:/";
    }


}
