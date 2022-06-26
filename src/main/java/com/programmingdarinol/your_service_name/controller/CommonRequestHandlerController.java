package com.programmingdarinol.your_service_name.controller;

import com.programmingdarinol.your_service_name.model.dto.AppContext;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
public class CommonRequestHandlerController {

  @ModelAttribute
  public AppContext getHeadersFromInterceptor(ServerWebExchange request) {
    return request.getAttribute("appContext");
  }
}
