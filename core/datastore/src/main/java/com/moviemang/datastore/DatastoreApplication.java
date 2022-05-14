package com.moviemang.datastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.moviemang.datastore.config")
public class DatastoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatastoreApplication.class, args);
    }

}
