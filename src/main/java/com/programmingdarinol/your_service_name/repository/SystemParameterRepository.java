package com.programmingdarinol.your_service_name.repository;

import com.programmingdarinol.your_service_name.model.entity.SystemParameter;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SystemParameterRepository extends ReactiveMongoRepository<SystemParameter, String>, SystemParameterRepositoryCustom {

  Mono<SystemParameter> findByVariable(String variable);
}
