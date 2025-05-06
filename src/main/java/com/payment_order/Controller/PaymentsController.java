package com.payment_order.Controller;

import com.payment_order.Entity.Payment;
import com.payment_order.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class PaymentsController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/")
    public String goToFirstView(Model model) {
        List<Payment> list = paymentService.getAllPays();
        model.addAttribute("tab_lines", list);
        return "first-view";
    }

    @GetMapping("/enter-new-pay.html")
    public String goToEnterForm() {
        return "enter-new-pay";
    }

    @GetMapping("/added")
    public String addPayment(Payment payment) {
        paymentService.addNewPay(payment);
        return "redirect:/enter-new-pay.html";
    }

    @GetMapping("/deletePay")
    public String delete(int id) {
        paymentService.deletePay(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveToFile(RedirectAttributes redirectAttributes) {
        try {
            // исправить заменить на DTO
            paymentService.exportGeneralPayments(paymentService.getAllPays());
            redirectAttributes.addFlashAttribute("result", "Выгрузка успешно завершена."); //добавляем новые атрибуты при переходе на главную страницу,чтобы не перезаполнять список платежей
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("result", e.getMessage());
        }

        return "redirect:/";
    }


}
