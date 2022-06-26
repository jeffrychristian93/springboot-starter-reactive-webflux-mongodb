package com.programmingdarinol.your_service_name.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "mongodb-client")
public record ReactiveMongoProperties(String host,
                                      String port,
                                      String uri,
                                      String database,
                                      String authenticationDatabase,
                                      String gridFsDatabase,
                                      String username,
                                      char[] password,
                                      Class<?> fieldNamingStrategy,
                                      Integer maxConnectionPerHost,
                                      Integer minConnectionsPerHost,
                                      Integer connectTimeout,
                                      Integer readTimeout,
                                      Integer maxWaitTime,
                                      Integer socketTimeout,
                                      Integer heartbeatFrequency,
                                      Integer maxConnectionIdleTime,
                                      Integer maxConnectionLifeTime,
                                      Integer minHeartbeatFrequency,
                                      Integer maxQueueSize,
                                      Integer waitQueueSize,
                                      Integer serverSelectionTimeout,
                                      Integer receiveBufferSize,
                                      Integer sendBufferSize,
                                      String readPreference) {}