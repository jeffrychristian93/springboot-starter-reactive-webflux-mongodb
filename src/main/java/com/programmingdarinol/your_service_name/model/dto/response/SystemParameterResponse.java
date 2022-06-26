package com.programmingdarinol.your_service_name.model.dto.response;

import java.util.Date;

public record SystemParameterResponse (
    String id,
    String variable,
    String value,
    String description,
    Date createdDate,
    String createdBy,
    Date updatedDate,
    String updatedBy,
    Boolean deleted) {
}
