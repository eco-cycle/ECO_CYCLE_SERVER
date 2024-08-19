package com.hackathon.ecocycle.domain.member.api;

import com.hackathon.ecocycle.domain.member.application.MemberService;
import com.hackathon.ecocycle.domain.member.dto.request.InfoRequestDto;
import com.hackathon.ecocycle.domain.member.dto.response.MemberInfoResponseDto;
import com.hackathon.ecocycle.domain.member.dto.response.NewMemberResponseDto;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.exception.dto.ErrorResponse;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "신규 회원 확인", description = "신규 회원 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신규 회원 확인 조회 성공", content = @Content(schema = @Schema(implementation = NewMemberResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/new")
    public ResponseTemplate<NewMemberResponseDto> isNewMember(@AuthenticationPrincipal String email) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "신규 회원 확인 조회 성공", memberService.isNewMember(email));
    }

    @Operation(summary = "회원정보 조회", description = "회원정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 조회 성공", content = @Content(schema = @Schema(implementation = MemberInfoResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/info")
    public ResponseTemplate<?> getMemberInfo(@AuthenticationPrincipal String email) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "회원정보 조회 성공",memberService.getMemberInfo(email));
    }

    @Operation(summary = "회원정보 수정", description = "회원정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/info")
    public ResponseTemplate<?> updateMemberInfo(@AuthenticationPrincipal String email, @RequestBody InfoRequestDto infoRequestDto) throws MemberNotFoundException {
        memberService.updateInfoMember(email, infoRequestDto);
        return new ResponseTemplate<>(HttpStatus.OK, "회원정보 수정 성공");
    }
}
