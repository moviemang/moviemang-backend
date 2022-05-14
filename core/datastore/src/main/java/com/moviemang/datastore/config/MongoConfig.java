package com.moviemang.datastore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@PropertySource(value = "classpath:${spring.profiles.active}/mongodb.yml", factory = YamlPropertySourceFactory.class)
@EnableMongoRepositories(basePackages = "com.moviemang.datastore.repository.mongo")
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    @ConfigurationProperties(prefix = "mongodb")
    public MongoProperties mongoProperties(){
        return new MongoProperties();
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoProperties().getConnectionString());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }

}




