package com.payment_order.Service;

import com.payment_order.DTO.ExportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExportReportServiceImpl implements ExportReportService {
    @Autowired
    PaymentService paymentService;
    @Autowired
    ReportService reportService;
    @Value("${report.directory}")
    private String reportDirectory;
    @Value("${report.general.file}")
    private String generalReportFile;
    @Value("${report.recipient.file}")
    private String recipientReportFile;
    @Value("${report.purpose.file}")
    private String purposeReportFile;


    public ExportDTO saveToFile(String fileName, List<String> lines) throws IOException {
        Path directory = Paths.get(reportDirectory);
        // Проверяем существование директории и создаём её, если она ещё не создана
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        // Формируем полный путь к файлу
        Path fullPath = directory.resolve(fileName);
        Files.write(fullPath, lines, StandardCharsets.UTF_8);
        return new ExportDTO("Выгрузка успешно завершена.");
    }

    @Override
    public ExportDTO exportGeneralPayments() {
        try {
            return saveToFile(generalReportFile, paymentService.generateGeneralPayments());
        } catch (IOException e) {
            return new ExportDTO(e.getMessage());
        }
    }

    @Override
    public ExportDTO exportPaymentsByPurpose() {
        try {
            return saveToFile(purposeReportFile, reportService.generatePaymentsByPurposeReport());
        } catch (IOException e) {
            return new ExportDTO(e.getMessage());
        }
    }

    @Override
    public ExportDTO exportPaymentsByRecipient() {
        try {
            return saveToFile(recipientReportFile, reportService.generatePaymentsByRecipientReport());
        } catch (IOException e) {
            return new ExportDTO(e.getMessage());
        }
    }
}
