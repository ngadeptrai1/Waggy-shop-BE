package com.edu.authen.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileHandleService {

    String storeFile(MultipartFile thumbnail) throws IOException;
    void storeFile(MultipartFile multipartFile , String fileName) throws IOException;
}
