package com.company.restclientdemo.dto.response;

import java.time.LocalDateTime;

public record UserUpdateResponse(
        String name,
        String job,
        LocalDateTime updatedAt
) {
}
