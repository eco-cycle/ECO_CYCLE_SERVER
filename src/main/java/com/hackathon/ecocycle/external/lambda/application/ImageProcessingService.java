package com.hackathon.ecocycle.external.lambda.application;

import com.hackathon.ecocycle.external.lambda.dto.PredictedResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingService {

    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String externalApiUrl;

    public PredictedResponseDto predictImage(MultipartFile file) {

        HttpEntity<MultiValueMap<String, Object>> requestEntity = createRequestEntity(file);
        Map<String, Object> responseBody = sendRequest(requestEntity);
        return mapToPredictedResponseDto(responseBody);

    }

    private HttpEntity<MultiValueMap<String, Object>> createRequestEntity(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return new HttpEntity<>(body, headers);
    }

    private Map<String, Object> sendRequest(HttpEntity<MultiValueMap<String, Object>> requestEntity) {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(externalApiUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        return response.getBody();
    }

    private PredictedResponseDto mapToPredictedResponseDto(Map<String, Object> responseBody) {
        return PredictedResponseDto.builder().predictedClass((String) responseBody.get("predicted_class")).build();
    }
}
