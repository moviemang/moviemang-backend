package com.moviemang.playlist.controller;

import com.moviemang.playlist.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PlaylistController {

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/test")
    public void getTest(){
        log.info("GetMapping");
    }

    @PostMapping( "/test")
    public void postTest(){
        log.info("PostMapping");
    }

    @DeleteMapping("/test")
    public void delteTest(){
        log.info("DeleteMapping");
    }
}
