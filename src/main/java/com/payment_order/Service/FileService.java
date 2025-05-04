package com.payment_order.Service;

import com.payment_order.Entity.Payment;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
   public List<Payment> getList();

    public List<Payment> getDataFromXlsFile(File file) throws BiffException, IOException;

    public void deleteByNumber(int number);
}


