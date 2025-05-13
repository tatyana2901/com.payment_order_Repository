package com.payment_order.Controller;

import com.payment_order.DTO.UploadRequestDTO;
import com.payment_order.DTO.UploadResponseDTO;
import com.payment_order.Entity.Payment;
import com.payment_order.Service.UploadService;
import com.payment_order.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String doUpload(UploadRequestDTO requestDTO, Model model) { //добавить валидацию???
        UploadResponseDTO uploadResponseDTO = uploadService.getDataFromFile(requestDTO);
        List<Payment> payments = uploadService.getList();
        model.addAttribute("tab_lines", payments);
        model.addAttribute("response", uploadResponseDTO);
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
