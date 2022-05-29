package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.playlist.dto.MyReview;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    CommonResponse myReview(MyReview.Request request, Pageable pageRequest);
}
