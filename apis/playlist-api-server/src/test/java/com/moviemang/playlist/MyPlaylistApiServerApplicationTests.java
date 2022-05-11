package com.moviemang.playlist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@Profile("local")
@AutoConfigureMockMvc
@SpringBootTest
class MyPlaylistApiServerApplicationTests {

    void contextLoad(){

    }
}
