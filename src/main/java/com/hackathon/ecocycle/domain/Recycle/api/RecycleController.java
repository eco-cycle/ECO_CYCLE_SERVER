package com.hackathon.ecocycle.domain.Recycle.api;

import com.hackathon.ecocycle.domain.Recycle.application.RecycleService;
import com.hackathon.ecocycle.domain.Recycle.dto.request.RecycleRequestDto;
import com.hackathon.ecocycle.domain.Recycle.dto.response.RecycleResponseDto;
import com.hackathon.ecocycle.domain.Recycle.exception.RecycleNotFoundException;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recycle")
@RequiredArgsConstructor
public class RecycleController {
    private final RecycleService recycleService;

    @PostMapping("/create")
    public ResponseTemplate<?> createRecycle(@AuthenticationPrincipal String email, @RequestPart(required = true, value = "image") MultipartFile image, @RequestPart(required = true, value = "recycleRequestDto") RecycleRequestDto recycleRequestDto) throws MemberNotFoundException, IOException {
        recycleService.createRecycle(email, image, recycleRequestDto);
        return new ResponseTemplate<>(HttpStatus.OK, "Recycle created");
    }

    @GetMapping("/item/{id}")
    public ResponseTemplate<?> getRecycle(@PathVariable Long id) throws RecycleNotFoundException, MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "Recycle retrieved", recycleService.getRecycle(id));
    }

    @GetMapping("/sale")
    public ResponseTemplate<List<RecycleResponseDto>> getAllRecycleSale(@AuthenticationPrincipal String email) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "자원 거래 판매하기 조회 성공", recycleService.getAllRecycleSale(email));
    }
    @GetMapping("/purchase")
    public ResponseTemplate<List<RecycleResponseDto>> getAllRecyclePurchase(@AuthenticationPrincipal String email) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "자원 거래 구매하기 조회 성공", recycleService.getAllRecyclePurchase(email));
    }
}
