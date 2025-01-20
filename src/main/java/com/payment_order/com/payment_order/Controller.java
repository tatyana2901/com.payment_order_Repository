package com.payment_order.com.payment_order;

import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    PaymentRepository pr;
    @Autowired
    PaymentService ps;
    @Autowired
    FileService fs;

    @GetMapping("/")
    public String goToFirstView(Model model) {
        List<Payment> list = pr.findAll();
        model.addAttribute("tab_lines", list);
        return "first-view";
    }

    @GetMapping("/enter-new-pay.html")
    public String goToEnterForm() {
        return "enter-new-pay";
    }

    @GetMapping("/added")
    public String addPayment(String data, String recipient, String sum,
                             String num, String purpose, Model model) {

            pr.save(ps.getNewPaymentFromEnterForm(data, recipient, sum, num, purpose));


        return "redirect:/enter-new-pay.html";
    }

    @GetMapping("/report_by_recipient.html")
    public String getRecipientReport(Model model) {
        var report = pr.totalSumByRecipient();
        var str = pr.totalSumByRecipient().stream().map((Function<SumByRecip, Object>) sumByRecip -> List.of(sumByRecip.getRecipient(), sumByRecip.getTotal())).toList();
        model.addAttribute("report", report);
        model.addAttribute("chartData", str);
        return "report_by_recipient";
    }

    @GetMapping("/report_by_purpose.html")
    public String getPurposeReport(Model model) {
        var report = pr.totalSumByPurpose();
        var str = pr.totalSumByPurpose().stream().map((Function<SumByPurpose, Object>) sumByPurpose -> List.of(sumByPurpose.getPurpose(), sumByPurpose.getTotal())).toList();
        model.addAttribute("report", report);
        model.addAttribute("chartData", str);
        return "report_by_purpose";
    }


    @PostMapping("/save")
    public String saveToFile(Model model) {
        goToFirstView(model);
        try {
            ps.save("general", pr.findAll());
            model.addAttribute("result", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("result", e.getMessage());
        }

        return "first-view";
    }

    @PostMapping("/savepurp")
    public String saveByPurp(Model model) {
        getPurposeReport(model);
        try {
            ps.save("by_purpose", pr.findAll());
            model.addAttribute("purpres", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("purpres", e.getMessage());
        }
        return "report_by_purpose";
    }

    @PostMapping("/saverecip")
    public String saveByRecip(Model model) {
        getRecipientReport(model);
        try {
            ps.save("by_recip", pr.findAll());
            model.addAttribute("recres", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("recres", e.getMessage());
        }
        return "report_by_recipient";
    }

    @GetMapping("/deletePay")
    public String delete(int id) {
        pr.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/upload_pays.html")
    String index(Model model) {
        List<Payment> list = fs.getList();
        model.addAttribute("tab_lines", list);
        // System.out.println("тук-тук");
        return "upload_pays";
    }

    @PostMapping("/upload")
    public String doUpload(MultipartFile xfile, Model model) throws BiffException, IOException {
        if (xfile == null || xfile.isEmpty()) {
            System.out.println("нет файла");
            model.addAttribute("message", "нет файла");
        } else {
            System.out.println("получен файл " + xfile.getOriginalFilename());
            System.out.println("xfile.getSize() = " + xfile.getSize());
            model.addAttribute("message", "получен файл " + xfile.getOriginalFilename());
            model.addAttribute("size", xfile.getSize());
            File file = File.createTempFile(xfile.getOriginalFilename(), ".xls");
            System.out.println(file);
            try {
                //Сохранение файла на сервере
                System.out.println("Сохраняем");
                xfile.transferTo(file);
            } catch (IOException e) {
                System.out.println("не удалось сохранить файл: " + e.getMessage());
                model.addAttribute("errorMessage", "не удалось сохранить файл: " + e.getMessage());
            }

            //АНАЛИЗ файла

            List<Payment> list = fs.getDataFromXlsFile(file);
            model.addAttribute("tab_lines", list);


            System.out.println(file.delete()); //удаление файла, если он больше не нужен
        }
        return "upload_pays";
    }

    @GetMapping("/rejectPay")
    public String reject(int number) {
        fs.deleteByNumber(number);
        return "redirect:/upload_pays.html";
    }
    @PostMapping("/write_on_database")
    public String writePaysToDB(Model model){

        pr.saveAll(fs.getList());
        model.addAttribute("upload_result","В базу загружено платежей:" + fs.getList().size());

        return "upload_pays";
    }

    //проверить на уникальность платежи, удалить дубли из списка - добавить такую кнопку
    //добавить в обработчик ошибок еще ситуаций с ошибками


}
