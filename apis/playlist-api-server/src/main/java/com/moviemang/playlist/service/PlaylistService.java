package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.Playlist;
import com.moviemang.playlist.dto.PlaylistInfo;
import org.springframework.data.domain.Pageable;

import com.moviemang.coreutils.common.response.CommonResponse;

import java.util.List;

public interface PlaylistService {
    CommonResponse<MyPlaylist> myPlaylist(CommonParam commonParam, Pageable pageable);
    CommonResponse save(Playlist.Request playlist);

    CommonResponse lastestPlaylist();

    CommonResponse<List<PlaylistInfo>> playlistOrderByLikeCount();
}
