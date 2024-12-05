package com.payment_order.com.payment_order;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    /* List<Payment> pays = new ArrayList<>();*/
    //  Map<String, Double> sumByRecip = new HashMap<>();
    //  Map<Enum, Double> sumByPurp = new HashMap<>();


    public Payment add(String date, String recipient, String sum, String num, String purpose) {
        String[] dates = date.split("-");
        LocalDate ld = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
        return new Payment(0, recipient, ld, Double.parseDouble(sum), Purpose.valueOf(purpose), Integer.parseInt(num));
    }

    public Map<String, Double> getSumByRecip(List<Payment> pays) {
        return pays.stream()
                .collect(Collectors.toMap((Payment::getRecipient), (Payment::getSum), Double::sum
                ));
    }

    public Map<Purpose, Double> getSumByPurp(List<Payment> pays) {
        return
                pays.stream()
                        .collect(Collectors.toMap((Payment::getPurpose), (Payment::getSum), Double::sum
                        ));

    }

    public void save(String typeOfReport,List <Payment> pays) throws IOException {
        String fname = null;
        List<String> lines = null;
        switch (typeOfReport) {
            case "general":
                fname = "saved_payments.txt";
                lines = pays.stream().map(payment -> payment.getNumber() + ";" + payment.getDate() + ";" + payment.getSum() + ";" + payment.getRecipient() + ";" + payment.getPurpose()).toList();
                break;
            case "by_purpose":
                fname = "payments_by_purpose.txt";
                lines = getSumByPurp(pays).entrySet().stream().map(enumDoubleEntry -> enumDoubleEntry.getKey().toString() + ";" + enumDoubleEntry.getValue().toString()).toList();
                break;
            case "by_recip":
                fname = "payments_by_recipient.txt";
                lines = getSumByRecip(pays).entrySet()
                        .stream()
                        .map(x -> x.getKey() + ";" + x.getValue().toString())
                        .toList();
                break;
        }
        Path file = Paths.get(fname);
        Files.write(file, lines, StandardCharsets.UTF_8);
    }


}
