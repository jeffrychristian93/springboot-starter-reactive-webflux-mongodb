package com.programmingdarinol.your_service_name.configuration;

import com.programmingdarinol.your_service_name.model.dto.AppContext;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class InterceptorRequest implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    final HttpHeaders headers = exchange.getRequest().getHeaders();

    var entries = headers.entrySet();
    var mapHeaders = entries.stream()
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    var requestId = Optional.ofNullable(mapHeaders.get("REQUEST_ID")).map(data -> data.stream().findFirst().orElse(""));
    var username = Optional.ofNullable(mapHeaders.get("USERNAME")).map(data -> data.stream().findFirst().orElse(""));

    exchange.getAttributes().put("appContext", new AppContext(requestId.orElse(""), username.orElse("")));
    return chain.filter(exchange);
  }
}
