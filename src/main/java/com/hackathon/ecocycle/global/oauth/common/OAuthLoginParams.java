package com.hackathon.ecocycle.global.oauth.common;


import com.hackathon.ecocycle.domain.member.domain.entity.SocialType;
import org.springframework.util.MultiValueMap;


public interface OAuthLoginParams {
    SocialType oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
