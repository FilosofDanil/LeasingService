package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Images;
import com.example.wohnungsuchen.repositories.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImagesRepository imagesRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public void addFile(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(resultFileName));
        Images images = Images.builder().link(file.getOriginalFilename()).build();
        imagesRepository.save(images);
    }
}
