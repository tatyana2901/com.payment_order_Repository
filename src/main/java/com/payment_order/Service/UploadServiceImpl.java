package com.payment_order.Service;

import com.payment_order.Entity.Payment;
import com.payment_order.Entity.Purpose;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UploadServiceImpl implements UploadService {

    private List<Payment> list = new ArrayList<>();

    public List<Payment> getList() {
        return list;
    }


    public void deleteByNumber(int number) {
        Optional<Payment> p = list.stream()
                .filter(payment -> payment.getNumber() == number)
                .findFirst();
        p.ifPresent(payment -> list.remove(payment));
    }

    //ОБРАБОТАТЬ ВОЗМОНЫЕ ОШИБКИ
    public List<Payment> getDataFromFile(File file)  {

        list.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] item = line.split(";");
                list.add(stringToPayment(item));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Payment stringToPayment(String[] line) {

        Payment payment = new Payment();

        int number = Integer.parseInt(line[0]);
        LocalDate date = LocalDate.parse(line[1]);
        double sum = Double.parseDouble(line[2]);
        String recipient = line[3];
        Purpose purpose = Purpose.valueOf(line[4]);

        payment.setDate(date);
        payment.setNumber(number);
        payment.setSum(sum);
        payment.setPurpose(purpose);
        payment.setRecipient(recipient);

        System.out.println(payment);
        return payment;

    }
}
