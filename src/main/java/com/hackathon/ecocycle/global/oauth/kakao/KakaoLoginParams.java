package com.hackathon.ecocycle.global.oauth.kakao;

import com.hackathon.ecocycle.domain.member.domain.entity.SocialType;
import com.hackathon.ecocycle.global.oauth.common.OAuthLoginParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
    private String authorizationCode;

    @Override
    public SocialType oAuthProvider() {
        return SocialType.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}
