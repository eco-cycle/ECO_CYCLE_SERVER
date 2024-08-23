package com.hackathon.ecocycle.domain.Recycle.application;

import com.hackathon.ecocycle.domain.Recycle.domain.entity.Recycle;
import com.hackathon.ecocycle.domain.Recycle.domain.repository.RecycleRepository;
import com.hackathon.ecocycle.domain.Recycle.dto.request.RecycleRequestDto;
import com.hackathon.ecocycle.domain.Recycle.dto.response.RecycleResponseDto;
import com.hackathon.ecocycle.domain.Recycle.exception.RecycleNotFoundException;
import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import com.hackathon.ecocycle.global.image.service.ImageService;
import com.hackathon.ecocycle.global.utils.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecycleService {
    private final RecycleRepository recycleRepository;
    private final ImageService imageService;
    private final GlobalUtil globalUtil;

    public void createRecycle(String email, MultipartFile image, RecycleRequestDto recycleRequestDto) throws MemberNotFoundException, IOException {
        Member member = globalUtil.findByMemberWithEmail(email);

        String imageUrl = imageService.uploadImage(image);

        Recycle recycle = Recycle.builder()
                .member(member)
                .type(recycleRequestDto.type())
                .price(recycleRequestDto.price())
                .imageUrl(imageUrl)
                .build();
        recycleRepository.save(recycle);
    }

    @Transactional(readOnly = true)
    public RecycleResponseDto getRecycle(Long id) throws RecycleNotFoundException, MemberNotFoundException {
        Recycle recycle = recycleRepository.findById(id).orElseThrow(() -> new RecycleNotFoundException(ErrorCode.RECYCLE_NOT_FOUND));
        Member member = globalUtil.findByMemberWithEmail(recycle.getMember().getEmail());

        return RecycleResponseDto.builder()
                .id(recycle.getRecycleId())
                .price(recycle.getPrice())
                .type(recycle.getType())
                .imageUrl(recycle.getImageUrl())
                .nickname(member.getNickname())
                .location(member.getLocation())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecycleResponseDto> getAllRecycleSale(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);

        List<Recycle> recycleList = recycleRepository.findAll();

        return recycleList.stream().filter(
                recycle -> recycle.getMember().equals(member)
        ).map(
                recycle -> RecycleResponseDto.from(recycle, member)
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecycleResponseDto> getAllRecyclePurchase(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);

        List<Recycle> recycleList = recycleRepository.findAll();

        return recycleList.stream().filter(
                recycle -> !recycle.getMember().equals(member)
        ).map(
                recycle -> {
                    try {
                        Recycle recycleEntity = recycleRepository.findById(recycle.getRecycleId()).orElseThrow(() -> new RecycleNotFoundException(ErrorCode.RECYCLE_NOT_FOUND));
                        Member recycleOwner = recycleEntity.getMember();

                        return RecycleResponseDto.from(recycle, recycleOwner);
                    } catch (RecycleNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).collect(Collectors.toList());
    }
}
