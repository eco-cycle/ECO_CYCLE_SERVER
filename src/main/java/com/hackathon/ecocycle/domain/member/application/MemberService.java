package com.hackathon.ecocycle.domain.member.application;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.domain.member.dto.request.InfoRequestDto;
import com.hackathon.ecocycle.domain.member.dto.response.MemberInfoResponseDto;
import com.hackathon.ecocycle.domain.member.dto.response.NewMemberResponseDto;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.image.exception.ImageNotFoundException;
import com.hackathon.ecocycle.global.image.service.ImageService;
import com.hackathon.ecocycle.global.utils.GlobalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final GlobalUtil globalUtil;
    private final ImageService imageService;

    public NewMemberResponseDto isNewMember(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        return NewMemberResponseDto.builder()
                .isNew(member.getLocation() == null)
                .build();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponseDto getMemberInfo(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        return MemberInfoResponseDto.from(member);
    }

    @Transactional
    public void updateInfoMember(String email, InfoRequestDto infoRequestDto) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        member.updateInfo(infoRequestDto.location());
    }

    @Transactional
    public void updateProfileImage(String email, MultipartFile image) throws MemberNotFoundException, IOException, ImageNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        if(!member.getImageUrl().isEmpty()) {
            imageService.deleteImage(member.getImageUrl());
        }
        member.updateProfileImage(imageService.uploadImage(image));
    }
}
