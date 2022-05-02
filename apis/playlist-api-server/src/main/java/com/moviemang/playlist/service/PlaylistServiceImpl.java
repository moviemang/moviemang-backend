package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.PageInfo;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.repository.mongo.playList.PlaylistRepository;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.mapper.PlaylistMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private PlaylistMapper playlistMapper;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public CommonResponse myPlaylist(CommonParam commonParam, Pageable pageable) {
        MyPlaylist.Response.ResponseBuilder myPlaylist = MyPlaylist.Response.builder();
        try {
            List<Playlist> playlist = playlistRepository.findByMemberId(commonParam.getId(), pageable);
            if (CollectionUtils.isEmpty(playlist)){
                return CommonResponse.success(ErrorCode.COMMON_EMPTY_DATA);
            }
            List<PlaylistInfo> playlistInfo = playlistMapper.of(playlist);


            myPlaylist.playlist(playlistInfo);
            myPlaylist.page(PageInfo.builder()
                            .page(pageable.getPageNumber())
                            .size(pageable.getPageSize())
                            .build());

        } catch (Exception e){
            return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
        }

        return CommonResponse.success(myPlaylist.build());
    }
}
