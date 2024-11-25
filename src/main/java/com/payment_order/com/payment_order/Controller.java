package com.payment_order.com.payment_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    PaymentService ps;

    @GetMapping("/")
    public String goToFirstView() {
        return "first-view";
    }

    @GetMapping("/enter-new-pay.html")
    public String goToEnterForm() {
        return "enter-new-pay";
    }

    @GetMapping("/added")
    public String addPayment(String data, String sum, String recipient, String purpose, Model model) {
        System.out.println("КОНТРОЛЛЕР РАБОТАЕТ");
        ps.add(data,sum,recipient,purpose);

      /*  System.out.println(data);
        System.out.println(sum);
        System.out.println(recipient);
        System.out.println(purpose);*/

        return "first-view";//????
    }


}
