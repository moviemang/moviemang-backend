package com.moviemang.playlist;

import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.repository.mongo.playlist.CustomizedPlaylistRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PlaylistRepository playlistRepository;

    void contextLoad(){

    }

    @Test
    public void insertPlaylist(){
        playlistRepository.save(Playlist.builder()
                        .playlistTitle("test")
                        .playlistDescription("test")
                        .memberId(1L)

                .build());
    }


}
