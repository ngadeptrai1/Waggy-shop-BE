package com.edu.authen.service.impl;

import com.edu.authen.service.FileHandleService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class FileHandleServiceImpl implements FileHandleService  {
    @Override
    public String storeFile(MultipartFile thumbnail ) throws IOException {
        String fileName = StringUtils.cleanPath(thumbnail.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString()+fileName;
        storeFile(thumbnail,newFileName);
        return newFileName;
    }
    @Override
    @Async
    public void storeFile(MultipartFile multipartFile, String fileName) throws IOException {
        try {
            Path uploadDir = Paths.get("uploads");
            if(!Files.exists(uploadDir)){
                Files.createDirectories(uploadDir);
            }
            Path destination = Paths.get(uploadDir.toString(), fileName);
            Files.copy(multipartFile.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException(e.getMessage());
        }

    }

}
