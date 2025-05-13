package com.payment_order.Service;

import com.payment_order.DTO.UploadRequestDTO;
import com.payment_order.DTO.UploadResponseDTO;
import com.payment_order.Entity.Payment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UploadService {
    public List<Payment> getList();

    public UploadResponseDTO getDataFromFile(UploadRequestDTO uploadRequestDTO);

    public void deleteByNumber(int number);
}


