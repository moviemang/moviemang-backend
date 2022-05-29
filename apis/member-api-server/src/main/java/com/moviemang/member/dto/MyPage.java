package com.moviemang.member.dto;

import com.moviemang.coreutils.model.vo.CommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


public class MyPage {
    @Data
    public static class Request extends CommonParam {

    }


    @Getter
    @Data
    @AllArgsConstructor
    @Builder
    public static class Response{
        private MyPageInfo myPageInfo;
    }
}
