package com.payment_order.com.payment_order;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class FileService {

    private List<Payment> list = new ArrayList<>();

    public List<Payment> getList() {
        return list;
    }

    public void deleteByNumber(int number) {
        Optional <Payment> p = list.stream().filter(new Predicate<Payment>() {
            @Override
            public boolean test(Payment payment) {
                return payment.getNumber() == number;
            }
        }).findFirst();
        p.ifPresent(payment -> list.remove(payment));
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
            list.add(payment);
        }
        return list;


    }
}
