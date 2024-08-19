package com.hackathon.ecocycle.global.oauth.common;


import com.hackathon.ecocycle.domain.member.domain.entity.SocialType;

public interface OAuthApiClient {
    SocialType oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
