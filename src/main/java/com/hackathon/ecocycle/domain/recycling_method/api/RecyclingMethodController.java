package com.hackathon.ecocycle.domain.recycling_method.api;

import com.hackathon.ecocycle.domain.recycling_method.application.RecyclingMethodService;
import com.hackathon.ecocycle.domain.recycling_method.dto.RecyclingMethodResponseDto;
import com.hackathon.ecocycle.global.image.exception.ImageNotFoundException;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Recycling Method Controller", description = "Recycling Method API")
public class RecyclingMethodController {
    private final RecyclingMethodService recyclingMethodService;

    @GetMapping("/recycling/method")
    public ResponseTemplate<List<RecyclingMethodResponseDto>> getRecyclingMethodList() {
        return new ResponseTemplate<>(HttpStatus.OK, "재활용 방법 조회 성공", recyclingMethodService.getRecyclingMethodList());
    }

    @PostMapping("/recycling/method")
    public ResponseTemplate<?> addRecyclingMethod(@RequestParam("file") MultipartFile[] file, @RequestParam("category") String category) throws IOException, ImageNotFoundException {
        recyclingMethodService.productAddImage(file, category);
        return new ResponseTemplate<>(HttpStatus.CREATED, "재활용 방법 추가 성공");
    }
}
