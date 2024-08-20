package com.hackathon.ecocycle.domain.member.application;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.domain.member.dto.request.InfoRequestDto;
import com.hackathon.ecocycle.domain.member.dto.request.LocationRequestDto;
import com.hackathon.ecocycle.domain.member.dto.response.MemberInfoResponseDto;
import com.hackathon.ecocycle.domain.member.dto.response.NewMemberResponseDto;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.image.exception.ImageNotFoundException;
import com.hackathon.ecocycle.global.image.service.ImageService;
import com.hackathon.ecocycle.global.utils.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final GlobalUtil globalUtil;
    private final ImageService imageService;

    public NewMemberResponseDto isNewMember(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        return NewMemberResponseDto.builder()
                .isNew(member.getLocation() == null || member.getNickname() == null)
                .build();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponseDto getMemberInfo(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        return MemberInfoResponseDto.from(member);
    }

    @Transactional
    public void updateLocation(String email, LocationRequestDto locationRequestDto) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        member.updateLocation(locationRequestDto.location());
    }

    @Transactional
    public void updateInfo(String email, InfoRequestDto infoRequestDto) throws MemberNotFoundException, IOException, ImageNotFoundException {
        log.info("Updating info for email: {}", email);
        Member member = globalUtil.findByMemberWithEmail(email);

        if (member.getImageUrl() != null) {
            log.info("Deleting existing image: {}", member.getImageUrl());
            imageService.deleteImage(member.getImageUrl());
        }

        String newImageUrl = infoRequestDto.image() != null? imageService.uploadImage(infoRequestDto.image()) : member.getImageUrl();
        log.info("New image uploaded: {}", newImageUrl);

        member.updateProfileInfo(newImageUrl, infoRequestDto.nickname());
        log.info("Member info updated successfully for email: {}", email);
    }

}
