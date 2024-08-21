package com.hackathon.ecocycle.external.ML.api;

import com.hackathon.ecocycle.external.ML.application.ImageProcessingService;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image-processing")
public class ImageProcessingController {

    private final ImageProcessingService imageProcessingService;

    @PostMapping("/predict")
    public ResponseTemplate<?> predict(@RequestParam("file") MultipartFile file) {

        return new ResponseTemplate<>(HttpStatus.OK, "이미지 분석 성공", imageProcessingService.predictImage(file));
    }
}


