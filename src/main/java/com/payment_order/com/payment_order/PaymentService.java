package com.payment_order.com.payment_order;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    List<Payment> pays = new ArrayList<>();

    public void add(String date, String sum, String recipient, String purpose) {

        String[] dates = date.split("-");
        LocalDate ld = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));

        pays.add(new Payment(recipient, ld,Double.parseDouble(sum),Purpose.valueOf(purpose)));
        System.out.println(pays);

    }

}
