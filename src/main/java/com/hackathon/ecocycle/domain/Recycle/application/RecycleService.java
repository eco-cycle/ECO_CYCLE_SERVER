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
import java.time.LocalDateTime;
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

        Recycle recycle = buildRecycle(recycleRequestDto, member, imageUrl);

        recycleRepository.save(recycle);
    }

    @Transactional(readOnly = true)
    public RecycleResponseDto getRecycle(Long id) throws RecycleNotFoundException {
        Recycle recycle = findRecycleById(id);
        return RecycleResponseDto.from(recycle, recycle.getMember());
    }

    @Transactional(readOnly = true)
    public List<RecycleResponseDto> getAllRecycleSale(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        List<Recycle> recycleList = recycleRepository.findAllByMember(member);

        return toRecycleResponseDtoList(recycleList);
    }

    @Transactional(readOnly = true)
    public List<RecycleResponseDto> getAllRecyclePurchase(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        List<Recycle> recycleList = recycleRepository.findAllByMemberNot(member);

        return toRecycleResponseDtoList(recycleList);
    }

    private Recycle buildRecycle(RecycleRequestDto dto, Member member, String imageUrl) {
        return Recycle.builder()
                .location(member.getLocation())
                .member(member)
                .type(dto.type())
                .price(dto.price())
                .createdAt(LocalDateTime.now())
                .imageUrl(imageUrl)
                .build();
    }

    private Recycle findRecycleById(Long id) throws RecycleNotFoundException {
        return recycleRepository.findById(id)
                .orElseThrow(() -> new RecycleNotFoundException(ErrorCode.RECYCLE_NOT_FOUND));
    }

    private List<RecycleResponseDto> toRecycleResponseDtoList(List<Recycle> recycleList) {
        return recycleList.stream()
                .map(recycle -> RecycleResponseDto.from(recycle, recycle.getMember()))
                .collect(Collectors.toList());
    }
}
