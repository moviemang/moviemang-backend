package com.moviemang.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.moviemang", "com.moviemang.datastore"})
@SpringBootApplication
public class MainApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApiServerApplication.class, args);
    }

}
