package com.programmingdarinol.your_service_name.repository;

import com.programmingdarinol.your_service_name.model.entity.SystemParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface SystemParameterRepositoryCustom {
  Mono<Page<SystemParameter>> findAllBy(Pageable pageable);
}
