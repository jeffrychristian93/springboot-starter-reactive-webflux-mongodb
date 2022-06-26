package com.programmingdarinol.your_service_name.service;

import com.programmingdarinol.your_service_name.model.dto.AppContext;
import com.programmingdarinol.your_service_name.model.entity.SystemParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface SystemParameterService {

  Mono<SystemParameter> create(AppContext context, SystemParameter systemParameter);
  Mono<SystemParameter> update(AppContext context, String variable, SystemParameter systemParameter);
  Mono<SystemParameter> softDelete(AppContext context, String id);
  Mono<SystemParameter> findById(AppContext context, String id);
  Mono<Page<SystemParameter>> search(AppContext context, Pageable pageable);
}
