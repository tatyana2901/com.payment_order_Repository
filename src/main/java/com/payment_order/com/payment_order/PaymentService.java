package com.payment_order.com.payment_order;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public void save(String typeOfReport, List<Payment> pays) throws IOException {
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

    public Purpose parsePurpose(String string) {

        return switch (string) {
            case "Оплата поставщику" -> Purpose.SUPPLIER_PAYMENT;
            case "Уплата налога" -> Purpose.TAX;
            case "Перечисление заработной платы работнику" -> Purpose.SALARY;
            case "Банковские операции" -> Purpose.BANK_TRANSACTION;
            default -> null;
        };
    }

    public List<Payment> getDataFromXlsFile(File file) throws BiffException, IOException {
        List<Payment> payments = new ArrayList();
        Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        System.out.println(rows);

        for (int i = 1; i < rows; i++) {
            Payment payment = new Payment();
            String date = sheet.getCell(1, i).getContents();
            String[] dates = date.split("\\.");

            String purpose = sheet.getCell(9, i).getContents();
            Purpose result = parsePurpose(purpose);
            if (result == null) {
                continue;
            }
           /* System.out.println(dates.length);
            if (dates.length == 1) {
                payments.add(payment);
                break;
            }*/
            LocalDate ld = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]));
            String sum = sheet.getCell(6, i).getContents().replaceAll("[\\s|\\u00A0]+", "").replace(",", ".");
            payment.setDate(ld);
            payment.setNumber(Integer.parseInt(sheet.getCell(3, i).getContents()));
            payment.setSum(Double.valueOf(sum));
            payment.setRecipient(sheet.getCell(8, i).getContents());
            payment.setPurpose(result);
            payments.add(payment);
        }
        return payments;


    }


}
