package com.payment_order.Service;

import com.payment_order.Entity.Payment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UploadService {
    public List<Payment> getList();

    public List<Payment> getDataFromFile(File file) throws IOException;

    public void deleteByNumber(int number);
}


