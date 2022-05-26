package com.moviemang.playlist.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.Playlist;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.service.PlaylistService;
import com.moviemang.security.uitls.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.security.Principal;

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

//    @GetMapping("/test")
//    public void getTest(HttpServletRequest httpServletRequest, Playlist.Request request){
//        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
//        System.out.println(request.getEmail());
//        System.out.println(request.getId());
//    }
    @GetMapping("/")
    public CommonResponse mainPlaylist(@PageableDefault(page = 1, size = 4) Pageable pageRequest) {
        return playlistService.lastestPlaylist(pageRequest);
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

}
