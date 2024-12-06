package com.group.docReservations.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {

    @Value("${mongo.url}")
    private String mongoUrl;

    @Value("${mongo.database:docDB}")
    private String databaseName;

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUrl))
                .applyToSocketSettings(builder -> builder.connectTimeout(10, TimeUnit.SECONDS))
                .build();
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(settings), databaseName));
    }
}
