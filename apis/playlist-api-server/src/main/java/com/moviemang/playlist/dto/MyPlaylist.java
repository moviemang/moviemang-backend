package com.moviemang.playlist.dto;


import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.PageInfo;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MyPlaylist {

    @Data
    public static class Request extends CommonParam {

    }


    @Data
    @Builder
    public static class Response{
        private List<PlaylistInfo> playlist;
        private PageInfo page;

    }

}
