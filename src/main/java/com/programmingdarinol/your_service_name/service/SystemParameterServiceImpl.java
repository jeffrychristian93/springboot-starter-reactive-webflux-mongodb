package com.programmingdarinol.your_service_name.service;

import com.programmingdarinol.your_service_name.model.dto.AppContext;
import com.programmingdarinol.your_service_name.model.entity.SystemParameter;
import com.programmingdarinol.your_service_name.repository.SystemParameterRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

  @Autowired
  SystemParameterRepository systemParameterRepository;

  @Override
  public Mono<SystemParameter> create(AppContext context, SystemParameter systemParameter) {
    //todo add validation if variable name is already exists
    systemParameter.setCreatedBy(context.username());
    return systemParameterRepository.save(systemParameter);
  }

  @Override
  public Mono<SystemParameter> update(AppContext context, String variable, SystemParameter systemParameter) {
    return systemParameterRepository.findByVariable(variable)
        //todo throw no data exists
        .map(data -> {
          data.setValue(systemParameter.getValue());
          data.setUpdatedBy(context.username());
          data.setUpdatedDate(new Date());
          return data;
        })
        .flatMap(systemParameterRepository::save);
  }

  @Override
  public Mono<SystemParameter> softDelete(AppContext context, String id) {
    return systemParameterRepository.findById(id)
        .map(data -> {
          data.setDeleted(true);
          return data;
        })
        .flatMap(systemParameterRepository::save);
  }

  @Override
  public Mono<SystemParameter> findById(AppContext context, String id) {
    return systemParameterRepository.findById(id);
  }

  @Override
  public Mono<Page<SystemParameter>> search(AppContext context, Pageable pageable) {
    return systemParameterRepository.findAllBy(pageable);
  }
}
