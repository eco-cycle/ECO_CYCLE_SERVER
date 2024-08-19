package com.hackathon.ecocycle.global.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import com.hackathon.ecocycle.global.image.domain.entity.Image;
import com.hackathon.ecocycle.global.image.domain.repository.ImageRepository;
import com.hackathon.ecocycle.global.image.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private static final String S3_BASE_URL_FORMAT = "https://%s.s3.ap-northeast-2.amazonaws.com/";

    // 단일 이미지 업로드
    public String uploadImage(MultipartFile file) throws IOException, ImageNotFoundException {
        validateFile(file);
        String fileName = generateFileName(file);
        String imageUrl = uploadToS3(file, fileName);
        saveImage(imageUrl);

        return imageUrl;
    }

    // 다중 이미지 업로드
    public List<String> uploadImages(MultipartFile[] files) throws IOException, ImageNotFoundException {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            images.add(uploadImage(file));
        }
        return images;
    }

    private String uploadToS3(MultipartFile file, String fileName) throws IOException {
        String imageUrl = String.format(S3_BASE_URL_FORMAT, bucket) + fileName;
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), getObjectMetadata(file)));
        return imageUrl;
    }

    public void deleteImage(String imageUrl) {
        String fileName = extractFileNameFromUrl(imageUrl);
        amazonS3.deleteObject(bucket, fileName);
    }

    private void saveImage(String imageUrl) {
        Image image = Image.builder()
                .imageUrl(imageUrl)
                .build();
        imageRepository.save(image);
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }

    private String extractFileNameFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    private void validateFile(MultipartFile file) throws ImageNotFoundException {
        if (file == null || file.isEmpty()) {
            throw new ImageNotFoundException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }
}
