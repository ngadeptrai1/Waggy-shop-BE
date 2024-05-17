package com.edu.authen.service.impl;

import com.cloudinary.Cloudinary;
import com.edu.authen.service.FileHandleService;
import lombok.RequiredArgsConstructor;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class FileHandleServiceImpl implements FileHandleService  {

    private final Cloudinary cloudinary;

    @Override
    public String storeFile(MultipartFile thumbnail ) throws IOException {
//        String fileName = StringUtils.cleanPath(thumbnail.getOriginalFilename());
        String newFileName = (String) uploadCoudary(thumbnail).get("url");
//        storeFile(thumbnail,newFileName);
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

    @Override
    @Async
    public Map uploadCoudary(MultipartFile file) throws IOException {
      try {
          Map data =  cloudinary.uploader().upload(file.getBytes(), Map.of());
          return data;
      }catch (IOException ex){
          throw new IOException("Failed to upload file");
      }
    }

}
