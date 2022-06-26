package com.programmingdarinol.your_service_name.repository;

import com.programmingdarinol.your_service_name.model.entity.SystemParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SystemParameterRepositoryImpl implements SystemParameterRepositoryCustom {

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Mono<Page<SystemParameter>> findAllBy(Pageable pageable) {
    Query query = new Query();
    query.with(pageable);

    Query countQuery = new Query();
    return Mono.zip(
        reactiveMongoTemplate.find(query, SystemParameter.class).collectList(),
        reactiveMongoTemplate.count(countQuery, SystemParameter.class),
        (systemParameters, total) -> new PageImpl<>(systemParameters, pageable, total));
  }
}
