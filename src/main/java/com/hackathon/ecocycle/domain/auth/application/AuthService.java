package com.hackathon.ecocycle.domain.auth.application;

import com.hackathon.ecocycle.domain.auth.dto.ReIssueRequestDto;
import com.hackathon.ecocycle.domain.auth.exception.InvalidTokenException;
import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.domain.member.domain.repository.MemberRepository;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import com.hackathon.ecocycle.global.jwt.domain.entity.Token;
import com.hackathon.ecocycle.global.jwt.domain.repository.TokenRepository;
import com.hackathon.ecocycle.global.jwt.dto.response.TokenDto;
import com.hackathon.ecocycle.global.jwt.util.JwtTokenProvider;
import com.hackathon.ecocycle.global.oauth.common.OAuthInfoResponse;
import com.hackathon.ecocycle.global.oauth.common.OAuthLoginParams;
import com.hackathon.ecocycle.global.oauth.exception.OAuthException;
import com.hackathon.ecocycle.global.oauth.service.RequestOAuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public TokenDto login(OAuthLoginParams params) throws OAuthException, MemberNotFoundException {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        String email = findOrCreateMember(oAuthInfoResponse);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        TokenDto tokenDto = jwtTokenProvider.generate(email);
        saveOrUpdateToken(member, tokenDto);

        return tokenDto;
    }

    private String findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getEmail)
                .orElseGet(() -> createNewMember(oAuthInfoResponse));
    }

    private String createNewMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                .socialType(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getEmail();
    }

    private void saveOrUpdateToken(Member member, TokenDto tokenDto) {
        Token token = tokenRepository.findByMember(member)
                .orElseGet(() -> createNewToken(member, tokenDto));

        token.updateRefreshToken(tokenDto.getRefreshToken());
        tokenRepository.save(token);
    }

    private Token createNewToken(Member member, TokenDto tokenDto) {
        return Token.builder()
                .member(member)
                .refreshToken(tokenDto.getRefreshToken())
                .build();
    }

    @Transactional
    public TokenDto generateAccessToken(ReIssueRequestDto reIssueRequestDto) throws MemberNotFoundException, InvalidTokenException {
        validateRefreshToken(reIssueRequestDto.getRefreshToken());

        Token token = tokenRepository.findByRefreshToken(reIssueRequestDto.getRefreshToken());

        Member member = memberRepository.findById(token.getMember().getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return createNewAccessToken(member, token.getRefreshToken());
    }

    private void validateRefreshToken(String refreshToken) throws InvalidTokenException {
        if (tokenRepository.findByRefreshToken(refreshToken) == null || !jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    private TokenDto createNewAccessToken(Member member, String refreshToken) {
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(jwtTokenProvider.generateAccessToken(member.getEmail()))
                .refreshToken(refreshToken)
                .build();
    }
}
