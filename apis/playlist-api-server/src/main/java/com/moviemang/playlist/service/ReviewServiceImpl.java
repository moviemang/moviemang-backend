package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.PageInfo;
import com.moviemang.datastore.entity.mongo.Review;
import com.moviemang.datastore.repository.mongo.review.ReviewRepository;
import com.moviemang.playlist.dto.MyReview;
import com.moviemang.playlist.mapper.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public CommonResponse myReview(MyReview.Request request, Pageable pageRequest) {
        MyReview.Response.ResponseBuilder myReviews = MyReview.Response.builder();
        try{
            List<Review> reviews = reviewRepository.findByMemberIdOrderByRegDate(request.getId(), pageRequest);
            if(CollectionUtils.isEmpty(reviews)){
                return CommonResponse.success(ErrorCode.COMMON_EMPTY_DATA);
            }

            myReviews.reviewInfos(reviewMapper.of(reviews));
            myReviews.page(PageInfo.builder()
                    .page(pageRequest.getPageNumber())
                    .size(pageRequest.getPageSize())
                    .build());

        } catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }

        return CommonResponse.success(myReviews.build());
    }
}
