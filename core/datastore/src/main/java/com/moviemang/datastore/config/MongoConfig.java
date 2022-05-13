package com.moviemang.datastore.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.moviemang.datastore.repository.mongo", mongoTemplateRef = "mongoTemplate")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoClient, "moviemang");
        return new MongoTemplate(factory);
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/moviemang");
        MongoCredential mongoCredential = MongoCredential.createCredential(
                                                    "moviemang",
                                                    "moviemang",
                                                    "moviemang-pass".toCharArray());

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(mongoCredential)
                .build();

        return MongoClients.create(mongoClientSettings);
    }



    @Override
    protected String getDatabaseName() {
        return "moviemang";
    }
}
