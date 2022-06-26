package com.programmingdarinol.your_service_name.controller;

import com.programmingdarinol.your_service_name.library.DTOHelper;
import com.programmingdarinol.your_service_name.model.dto.AppContext;
import com.programmingdarinol.your_service_name.model.dto.request.SystemParameterRequest;
import com.programmingdarinol.your_service_name.model.dto.response.SystemParameterResponse;
import com.programmingdarinol.your_service_name.model.entity.SystemParameter;
import com.programmingdarinol.your_service_name.service.SystemParameterService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/system-parameter")
@Api(value = "System Parameter Controller")
public record SystemParameterController(SystemParameterService systemParameterService) {

  @PostMapping("/create")
  @Operation(description = "This endpoint is for create new system parameter")
  public Mono<SystemParameterResponse> create(
      @RequestAttribute AppContext appContext,
      @RequestBody SystemParameterRequest request) {
    return systemParameterService
        .create(appContext, DTOHelper.mapTo(request, SystemParameter.class))
        .map(response -> DTOHelper.mapTo(response, SystemParameterResponse.class));
  }

  @PutMapping("/update")
  @Operation(description = "This endpoint is for update system parameter")
  public Mono<SystemParameterResponse> update(
      @RequestAttribute AppContext appContext,
      @RequestParam String variable,
      @RequestBody SystemParameterRequest request) {
    return systemParameterService
        .update(appContext, variable, DTOHelper.mapTo(request, SystemParameter.class))
        .map(response -> DTOHelper.mapTo(response, SystemParameterResponse.class));
  }

  @GetMapping("/{id}")
  @Operation(description = "This endpoint is for find system parameter by id")
  public Mono<SystemParameterResponse> findById(
      @RequestAttribute AppContext appContext,
      @PathVariable String id) {
    return systemParameterService.findById(appContext, id)
        .map(response -> DTOHelper.mapTo(response, SystemParameterResponse.class));
  }

  @DeleteMapping("/delete/{id}")
  @Operation(description = "This endpoint is for soft delete system parameter by id")
  public Mono<SystemParameterResponse> deleteById(
      @RequestAttribute AppContext appContext,
      @PathVariable String id) {
    return systemParameterService.softDelete(appContext, id)
        .map(response -> DTOHelper.mapTo(response, SystemParameterResponse.class));
  }

  @GetMapping("/search")
  @Operation(description = "This endpoint is for create new system parameter")
  public Mono<Page<SystemParameterResponse>> search(
      @RequestAttribute AppContext appContext,
      @PageableDefault(size = 20) Pageable pageable) {
    return systemParameterService.search(appContext, pageable)
        .map(systemParameters -> systemParameters.map(response
            -> DTOHelper.mapTo(response, SystemParameterResponse.class)));
  }
}