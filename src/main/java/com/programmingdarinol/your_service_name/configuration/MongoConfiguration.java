package com.programmingdarinol.your_service_name.configuration;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.ServerSettings;
import com.mongodb.connection.SocketSettings;
import com.mongodb.connection.netty.NettyStreamFactoryFactory;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.programmingdarinol.your_service_name.configuration.properties.ReactiveMongoProperties;
import io.netty.channel.nio.NioEventLoopGroup;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.programmingdarinol.your_service_name.repository")
@EnableReactiveMongoRepositories(value = "com.programmingdarinol.your_service_name.repository")
public class MongoConfiguration {

  private static final String DESCRIPTION = "mongodb";
  private final NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

  @Bean
  public MongoClientSettings mongoClientSettings(ReactiveMongoProperties reactiveMongoProperties) {
    final String[] hosts = reactiveMongoProperties.host().split(",");
    final String[] ports = reactiveMongoProperties.port().split(",");
    List<ServerAddress> serverAddresses = IntStream.range(0, hosts.length)
        .mapToObj(i -> new ServerAddress(hosts[i], Integer.parseInt(ports[i])))
        .collect(toList());
    Builder builder = MongoClientSettings.builder()
        .writeConcern(WriteConcern.ACKNOWLEDGED)
        .applyToConnectionPoolSettings(connectionPool -> connectionPool
            .applySettings(ConnectionPoolSettings.builder()
                .maxConnectionLifeTime(reactiveMongoProperties.maxConnectionLifeTime(),
                    MILLISECONDS)
                .maxConnectionIdleTime(reactiveMongoProperties.maxConnectionIdleTime(),
                    MILLISECONDS)
                .maxWaitTime(reactiveMongoProperties.maxWaitTime(), MILLISECONDS)
                .maxSize(reactiveMongoProperties.maxConnectionPerHost())
                .minSize(reactiveMongoProperties.minConnectionsPerHost())
                .build()))
        .readPreference(ReadPreference.valueOf(reactiveMongoProperties.readPreference()))
        .applyToClusterSettings(cluster -> cluster.applySettings(ClusterSettings.builder()
            .hosts(serverAddresses)
            .mode(hosts.length == 1 ? ClusterConnectionMode.SINGLE : ClusterConnectionMode.MULTIPLE)
            .serverSelectionTimeout(reactiveMongoProperties.serverSelectionTimeout(),
                MILLISECONDS)
            .build()))
        .applyToServerSettings(serverSetting -> serverSetting
            .applySettings(ServerSettings.builder()
                .heartbeatFrequency(reactiveMongoProperties.heartbeatFrequency(), MILLISECONDS)
                .minHeartbeatFrequency(reactiveMongoProperties.minHeartbeatFrequency(),
                    MILLISECONDS)
                .build()))
        .applyToSocketSettings(b -> b.applySettings(SocketSettings.builder()
            .connectTimeout(reactiveMongoProperties.connectTimeout(), MILLISECONDS)
            .readTimeout(reactiveMongoProperties.readTimeout(), MILLISECONDS)
            .receiveBufferSize(reactiveMongoProperties.receiveBufferSize())
            .sendBufferSize(reactiveMongoProperties.sendBufferSize()).build()))
        .retryReads(true)
        .retryWrites(true)
        .applicationName(DESCRIPTION)
        .streamFactoryFactory(NettyStreamFactoryFactory.builder()
            .eventLoopGroup(eventLoopGroup).build());
    if (StringUtils.isNotBlank(reactiveMongoProperties.username()) && Objects
        .nonNull(reactiveMongoProperties.password()) &&
        StringUtils.isNotBlank(reactiveMongoProperties.authenticationDatabase())) {
      builder.credential(MongoCredential
          .createScramSha1Credential(reactiveMongoProperties.username(),
              reactiveMongoProperties.authenticationDatabase(),
              reactiveMongoProperties.password()));
    }
    return builder.build();
  }

  @Bean
  public MongoClient mongoClient(ReactiveMongoProperties reactiveMongoProperties) {
    return MongoClients.create(mongoClientSettings(reactiveMongoProperties));
  }

  @Bean
  public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory(
      ReactiveMongoProperties reactiveMongoProperties) {
    return new SimpleReactiveMongoDatabaseFactory(mongoClient(reactiveMongoProperties),
        reactiveMongoProperties.database());
  }

  @Bean(name = "reactiveMongoTemplate")
  public ReactiveMongoTemplate reactiveMongoTemplate(ReactiveMongoDatabaseFactory databaseFactory) {
    return new ReactiveMongoTemplate(databaseFactory);
  }
}
