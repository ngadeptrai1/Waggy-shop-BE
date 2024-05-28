package com.edu.authen.service;

import com.edu.authen.model.Product;
import com.edu.authen.model.ProductImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface FileHandleService {

    void storeFile(MultipartFile multipartFile , String fileName) throws IOException;
    void uploadCoudary(MultipartFile file, ProductImage image) throws IOException;
    void uploadThumbnail(MultipartFile file, Product image) throws IOException;
}
