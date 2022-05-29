package com.moviemang.playlist.dto;

import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public class MyReview {
    @Data
    public static class Request extends CommonParam {

    }

    @Getter
    @Data
    @AllArgsConstructor
    @Builder
    public static class Response{
        private List<ReviewInfo> reviewInfos;
        private PageInfo page;
    }
}
