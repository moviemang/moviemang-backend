package com.moviemang.playlist.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.service.PlaylistService;
import com.moviemang.security.uitls.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class PlaylistController {

    private PlaylistService playlistService;
    private AuthenticationUtil authenticationUtil;

    @Autowired
    public PlaylistController(PlaylistService playlistService, AuthenticationUtil authenticationUtil) {
        this.playlistService = playlistService;
        this.authenticationUtil = authenticationUtil;
    }

//    @GetMapping("/test")
//    public void getTest(HttpServletRequest httpServletRequest, Playlist.Request request){
//        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
//        System.out.println(request.getEmail());
//        System.out.println(request.getId());
//    }

    @GetMapping("/me/playlist")
    public CommonResponse<MyPlaylist> myPlaylist(HttpServletRequest httpServletRequest,
                                                 Pageable pageRequest, MyPlaylist.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);

        return playlistService.myPlaylist(request, pageRequest);

    }



    @GetMapping("/playlistOrderByLike")
    public CommonResponse playlistOrderByLike(){
        return playlistService.playlistOrderByLike();
    }

}
