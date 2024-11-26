package com.payment_order.com.payment_order;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    List<Payment> pays = new ArrayList<>();


    public PaymentService() {

        pays.add(new Payment("Валя", LocalDate.of(2024, 2, 23), 1500.0, Purpose.SUPPLIER_PAYMENT, 98));
        pays.add(new Payment("Костя", LocalDate.of(2024, 9, 1), 982.550, Purpose.SUPPLIER_PAYMENT, 32));
        pays.add(new Payment("Валя", LocalDate.of(2024, 2, 23), 1500.0, Purpose.SALARY, 98));
        pays.add(new Payment("Налоговая", LocalDate.of(2024, 3, 19), 898.0, Purpose.TAX, 5));
    }

    public void add(String date, String sum, String recipient, String num, String purpose) {
        String[] dates = date.split("-");
        LocalDate ld = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
        pays.add(new Payment(recipient, ld, Double.parseDouble(sum), Purpose.valueOf(purpose), Integer.parseInt(num)));
    }

    public Map getSumByRecipMap() {
        Map<String, Double> sumByRecip =
                pays.stream()
                        .collect(Collectors.toMap((Payment::getRecipient), (Payment::getSum), Double::sum
                        ));
        return sumByRecip;

    }
    /*public Map getSumByRecipMap() {
        Map<String, Double> sumByRecip =
                pays.stream()
                        .collect(Collectors.toMap((new Function<Payment, String>() {
                                    @Override
                                    public String apply(Payment payment) {
                                        return payment.getRecipient();
                                    }
                                }), (new Function<Payment, Double>() {
                                    @Override
                                    public Double apply(Payment payment) {
                                        return payment.getSum();
                                    }
                                }), new BinaryOperator<Double>() {
                                    @Override
                                    public Double apply(Double aDouble, Double aDouble2) {
                                        return aDouble + aDouble2;
                                    }
                                }
                        ));
        return sumByRecip;*/


}
