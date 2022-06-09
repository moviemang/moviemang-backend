package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.playlist.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaylistService {
    CommonResponse<MyPlaylist> myPlaylist(CommonParam commonParam, Pageable pageable);
    CommonResponse save(Playlist.Request playlist);

    CommonResponse lastestPlaylist();

    CommonResponse<List<PlaylistInfo>> playlistOrderByLikeCount();
    CommonResponse deleteMovie(DeleteMovie.Request request);

    CommonResponse likeAdd(PlaylistLikeAdd.Request request);

    CommonResponse detail(PlaylistDetail.Request request);
}
