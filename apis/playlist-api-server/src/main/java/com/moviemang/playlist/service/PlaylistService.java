package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.Playlist;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.moviemang.coreutils.common.response.CommonResponse;

public interface PlaylistService {
    CommonResponse playlistOrderByLike();
    CommonResponse<MyPlaylist> myPlaylist(CommonParam commonParam, Pageable pageable);
    CommonResponse save(Playlist.Request playlist);
}
