package com.payment_order.Controller;

import com.payment_order.Entity.Payment;
import com.payment_order.Service.FileService;
import com.payment_order.Service.PaymentService;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class FilesController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ReportsController reportsController;
    @Autowired
    PaymentsController paymentsController;
    @Autowired
    FileService fileService;

    @PostMapping("/savepurp")
    public String saveByPurp(Model model) {
        //?????
        reportsController.getPurposeReport(model);
        try {
            paymentService.save("by_purpose", paymentService.getAllPays());
            model.addAttribute("purpres", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("purpres", e.getMessage());
        }
        return "report_by_purpose";
    }

    @PostMapping("/saverecip")
    public String saveByRecip(Model model) {
        //?????
        reportsController.getRecipientReport(model); //?????
        try {
            paymentService.save("by_recip", paymentService.getAllPays());
            model.addAttribute("recres", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("recres", e.getMessage());
        }
        return "report_by_recipient";
    }

    @PostMapping("/save")
    public String saveToFile(Model model) {
        //???
        paymentsController.goToFirstView(model);
        try {
            paymentService.save("general", paymentService.getAllPays());
            model.addAttribute("result", "Выгрузка успешно завершена.");
        } catch (IOException e) {
            model.addAttribute("result", e.getMessage());
        }

        return "first-view";
    }


    @GetMapping("/upload_pays.html")
    String index(Model model) {
        List<Payment> list = fileService.getList();
        model.addAttribute("tab_lines", list);
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

            List<Payment> list = fileService.getDataFromXlsFile(file);
            model.addAttribute("tab_lines", list);


            System.out.println(file.delete()); //удаление файла, если он больше не нужен
        }
        return "upload_pays";
    }

    @GetMapping("/rejectPay")
    public String reject(int number) {
        fileService.deleteByNumber(number);
        return "redirect:/upload_pays.html";
    }

    @PostMapping("/write_on_database")
    public String writePaysToDB(Model model) {

        paymentService.savePays(fileService.getList());
        model.addAttribute("upload_result", "В базу загружено платежей:" + fileService.getList().size());

        return "upload_pays";
    }
}
