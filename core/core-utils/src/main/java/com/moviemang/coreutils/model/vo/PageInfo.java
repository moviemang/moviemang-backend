package com.moviemang.coreutils.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
public class PageInfo {
    private Integer page;
    private Integer size;
}
