package com.moviemang.playlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.moviemang")
@SpringBootApplication
public class PlaylistApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaylistApiServerApplication.class, args);
    }

}
