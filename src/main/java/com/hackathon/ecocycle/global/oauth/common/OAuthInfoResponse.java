package com.hackathon.ecocycle.global.oauth.common;


import com.hackathon.ecocycle.domain.member.domain.entity.SocialType;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    SocialType getOAuthProvider();
}