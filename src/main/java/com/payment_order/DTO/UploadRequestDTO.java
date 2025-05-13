package com.payment_order.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UploadRequestDTO {

    private MultipartFile multipartFile;

    public UploadRequestDTO(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
