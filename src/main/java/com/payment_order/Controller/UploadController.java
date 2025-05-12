package com.payment_order.Controller;

import com.payment_order.Entity.Payment;
import com.payment_order.Service.UploadService;
import com.payment_order.Service.PaymentService;
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
public class UploadController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    UploadService uploadService;

    @GetMapping("/upload_pays.html")
    String index(Model model) {
        List<Payment> list = uploadService.getList();
        model.addAttribute("tab_lines", list);
        return "upload_pays";
    }

    @PostMapping("/upload")
    public String doUpload(MultipartFile xfile, Model model) throws IOException {
        if (xfile == null || xfile.isEmpty()) {

            model.addAttribute("message", "нет файла");
        } else {

            model.addAttribute("message", "получен файл " + xfile.getOriginalFilename());
            model.addAttribute("size", xfile.getSize());
            File file = File.createTempFile("payments_", "");

            try {
                //Сохранение файла на сервере

                xfile.transferTo(file);
            } catch (IOException e) {

                model.addAttribute("errorMessage", "не удалось сохранить файл: " + e.getMessage());
            }

            //АНАЛИЗ файла

            List<Payment> list = uploadService.getDataFromFile(file);
            model.addAttribute("tab_lines", list);

            System.out.println(file.delete());  //удаление файла, если он больше не нужен
        }
        return "upload_pays";
    }

    @GetMapping("/rejectPay")
    public String reject(int number) {
        uploadService.deleteByNumber(number);
        return "redirect:/upload_pays.html";
    }

    @PostMapping("/write_on_database")
    public String writePaysToDB(Model model) {

        paymentService.savePays(uploadService.getList());
        model.addAttribute("upload_result", "В базу загружено платежей:" + uploadService.getList().size());

        return "upload_pays";
    }
}
