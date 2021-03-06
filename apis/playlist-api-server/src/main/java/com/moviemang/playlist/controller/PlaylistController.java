package com.moviemang.playlist.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.playlist.dto.*;
import com.moviemang.playlist.service.PlaylistService;
import com.moviemang.security.uitls.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequestMapping(path = "/playlist")
@RestController
public class PlaylistController {

    private PlaylistService playlistService;
    private AuthenticationUtil authenticationUtil;

    @Autowired
    public PlaylistController(PlaylistService playlistService, AuthenticationUtil authenticationUtil) {
        this.playlistService = playlistService;
        this.authenticationUtil = authenticationUtil;
    }

    @GetMapping("/detail")
    public CommonResponse detail(@RequestBody PlaylistDetail.Request request){
        return playlistService.detail(request);
    }

    @GetMapping("/lastest")
    public CommonResponse mainPlaylist() {
        return playlistService.lastestPlaylist();
    }
    @GetMapping("/me")
    public CommonResponse myPlaylist(HttpServletRequest httpServletRequest,
                                     @PageableDefault(page = 1, size = 20) Pageable pageRequest, MyPlaylist.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        CommonResponse commonResponse = playlistService.myPlaylist(request, pageRequest);
        return commonResponse;

    }

    @PostMapping("/me")
    public CommonResponse<MyPlaylist> save(HttpServletRequest httpServletRequest, @RequestBody Playlist.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return playlistService.save(request);
    }

    @GetMapping("/playlistOrderByLikeCount")
    public CommonResponse<List<PlaylistInfo>> playlistOrderByLikeCount(){
        return playlistService.playlistOrderByLikeCount();
    }

    @DeleteMapping("/movie")
    public CommonResponse playlistMovieDelete(HttpServletRequest httpServletRequest, @RequestBody DeleteMovie.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return playlistService.deleteMovie(request);
    }

    @PostMapping("/like")
    public CommonResponse likeAdd(HttpServletRequest httpServletRequest, @RequestBody PlaylistLikeAdd.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return playlistService.likeAdd(request);
    }

}
