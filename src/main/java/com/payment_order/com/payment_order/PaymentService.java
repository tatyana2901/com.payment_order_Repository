package com.payment_order.com.payment_order;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Map getSumByRecip() {
        Map<String, Double> sumByRecip =
                pays.stream()
                        .collect(Collectors.toMap((Payment::getRecipient), (Payment::getSum), Double::sum
                        ));
        return sumByRecip;

    }

    public Map getSumByPurp() {
        Map<Enum, Double> sumByRecip =
                pays.stream()
                        .collect(Collectors.toMap((Payment::getPurpose), (Payment::getSum), Double::sum
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

    public Purpose parsePurpose(String string) {

        return switch (string) {
            case "оплата поставщику" -> Purpose.SUPPLIER_PAYMENT;
            case "оплата налогов" -> Purpose.TAX;
            case "зарплата" -> Purpose.SALARY;
            case "банковские операции" -> Purpose.BANK_TRANSACTION;
            default -> null;
        };


    }

    public boolean ifNumberUnique(int number) {
        return pays.stream().noneMatch(payment -> payment.getNumber() == number);
    }


    public void load() throws IOException {
        String fname = "payments.txt";
        Path file = Paths.get(fname);
        List<String> lines = Files.readAllLines(file);
        lines.stream().map(s -> {
            String[] strs = s.split(";");
         /*   if (ifNumberUnique(Integer.parseInt(strs[0]))) */
                String[] st = strs[1].split("-");
                return new Payment(strs[3], LocalDate.of(Integer.parseInt(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2])), Double.parseDouble(strs[2]), parsePurpose(strs[4]), Integer.parseInt(strs[0]));
            }).forEach(payment -> pays.add(payment));

    }


}