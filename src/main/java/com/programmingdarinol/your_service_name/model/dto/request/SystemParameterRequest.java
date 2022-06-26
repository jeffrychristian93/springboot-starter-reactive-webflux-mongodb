package com.programmingdarinol.your_service_name.model.dto.request;

public record SystemParameterRequest(
    String variable,
    String value,
    String description,
    Boolean deleted) {
}
