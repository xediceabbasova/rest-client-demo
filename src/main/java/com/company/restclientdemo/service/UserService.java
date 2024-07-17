package com.company.restclientdemo.service;

import com.company.restclientdemo.dto.response.UserDataResponse;
import com.company.restclientdemo.dto.request.UserRequest;
import com.company.restclientdemo.dto.response.UserCreateResponse;
import com.company.restclientdemo.dto.response.UserResponse;
import com.company.restclientdemo.dto.response.UserUpdateResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class UserService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public UserService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public UserResponse getListUsers(int page) {
        return restClient.get()
                .uri("/users?page={page}", page)
                .exchange((request, response) -> {
                    if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                        return objectMapper.readValue(response.getBody(), new TypeReference<>() {
                        });
                    } else {
                        throw new RuntimeException(response.getStatusCode().toString());
                    }
                });
    }

    public ResponseEntity<UserDataResponse> getSingleUser(int id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .toEntity(UserDataResponse.class);
    }

    public UserCreateResponse createUser(UserRequest request) {
        return restClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(UserCreateResponse.class);
    }

    public UserUpdateResponse updateUser(int id, UserRequest request) {
        return restClient.put()
                .uri("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, response) -> {
                    throw new RuntimeException("4xx error");
                })
                .body(UserUpdateResponse.class);
    }

    public void deleteUser(int id) {
        restClient.delete()
                .uri("/users/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
