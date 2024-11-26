package com.payment_order.com.payment_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    PaymentService ps;

    @GetMapping("/")
    public String goToFirstView(Model model) {
        model.addAttribute("tab_lines", ps.pays);
        return "first-view";
    }

    @GetMapping("/enter-new-pay.html")
    public String goToEnterForm() {
        return "enter-new-pay";
    }

    @GetMapping("/added")
    public String addPayment(String data, String sum, String recipient, String num, String purpose, Model model) {
        //  System.out.println("КОНТРОЛЛЕР РАБОТАЕТ");
        ps.add(data, sum, recipient, num, purpose);
        return "redirect:/enter-new-pay.html";
    }

    @GetMapping("/report_by_recipient.html")
    public String getRecipientReport(Model model) {
        var report = ps.getSumByRecip();
        model.addAttribute("map", report);
        return "report_by_recipient";
    }

    @GetMapping("/report_by_purpose.html")
    public String getPurposeReport(Model model) {
        var report = ps.getSumByPurp();
        model.addAttribute("map", report);
        return "report_by_purpose";
    }

    @PostMapping("/load")
    public String upLoadPays() throws IOException {


        System.out.println("КОНТРОЛЛЕР РАБОТАЕТ");
        ps.load();


        return "redirect:/";
    }


}