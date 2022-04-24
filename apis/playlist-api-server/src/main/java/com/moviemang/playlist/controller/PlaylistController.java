package com.moviemang.playlist.controller;

import com.moviemang.playlist.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PlaylistController {

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void getTest(){
        log.info("GetMapping");
    }

    @PostMapping( "/test")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTest(){
        log.info("PostMapping");
    }

    @DeleteMapping("/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delteTest(){
        log.info("DeleteMapping");
    }
}
