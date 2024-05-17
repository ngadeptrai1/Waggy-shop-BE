package com.edu.authen.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface FileHandleService {

    String storeFile(MultipartFile thumbnail) throws IOException;
    void storeFile(MultipartFile multipartFile , String fileName) throws IOException;
    Map uploadCoudary(MultipartFile file) throws IOException;
}
