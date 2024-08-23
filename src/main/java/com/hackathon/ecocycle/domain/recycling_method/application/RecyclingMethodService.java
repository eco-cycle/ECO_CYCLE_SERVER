package com.hackathon.ecocycle.domain.recycling_method.application;

import com.hackathon.ecocycle.domain.recycling_method.domain.entity.RecyclingMethod;
import com.hackathon.ecocycle.domain.recycling_method.domain.repository.RecyclingMethodRepository;
import com.hackathon.ecocycle.domain.recycling_method.dto.RecyclingMethodResponseDto;
import com.hackathon.ecocycle.global.image.exception.ImageNotFoundException;
import com.hackathon.ecocycle.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecyclingMethodService {
    private final ImageService imageService;
    private final RecyclingMethodRepository recyclingMethodRepository;

    @Transactional(readOnly = true)
    public List<RecyclingMethodResponseDto> getRecyclingMethodList() {
        List<RecyclingMethod> recyclingMethod = recyclingMethodRepository.findAll();
        List<RecyclingMethodResponseDto> recyclingMethodResponseDtoList = new ArrayList<>();

        for (RecyclingMethod recyclingMethodEntity : recyclingMethod) {
            recyclingMethodResponseDtoList.add(
                    RecyclingMethodResponseDto.builder()
                            .category(recyclingMethodEntity.getCategory())
                            .recyclingMethodImageUrlList(recyclingMethodEntity.getImageUrlList())
                            .build()
            );
        }

        return recyclingMethodResponseDtoList;
    }

    @Transactional
    public void productAddImage(MultipartFile[] image, String category) throws IOException, ImageNotFoundException {
        List<String> newImageUrlList = imageService.uploadImages(image);

        recyclingMethodRepository.findByCategory(category).orElseGet(
                () -> recyclingMethodRepository.save(
                        RecyclingMethod.builder()
                                .category(category)
                                .imageUrlList(newImageUrlList)
                                .build()
                )
        );
    }
}
