package com.hackathon.ecocycle.global.oauth.service;


import com.hackathon.ecocycle.domain.member.domain.entity.SocialType;
import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import com.hackathon.ecocycle.global.oauth.common.OAuthApiClient;
import com.hackathon.ecocycle.global.oauth.common.OAuthInfoResponse;
import com.hackathon.ecocycle.global.oauth.common.OAuthLoginParams;
import com.hackathon.ecocycle.global.oauth.exception.OAuthException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<SocialType, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) throws OAuthException {
        try {
            OAuthApiClient client = clients.get(params.oAuthProvider());
            String accessToken = client.requestAccessToken(params);
            return client.requestOauthInfo(accessToken);
        } catch (RestClientException e) {
            throw new OAuthException(ErrorCode.OAUTH_BAD_REQUEST);
        }
    }
}
