package com.moviemang.playlist.mapper;

import com.moviemang.datastore.entity.mongo.Review;
import com.moviemang.datastore.repository.mongo.review.ReviewRepository;
import com.moviemang.playlist.dto.MovieDetail;
import com.moviemang.playlist.dto.ReviewInfo;
import com.moviemang.playlist.util.MovieApiRequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2022-05-01T21:03:36+0900",
        comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 11.0.14.1 (JetBrains s.r.o.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    private ReviewRepository reviewRepository;
    private MovieApiRequestUtil movieApiRequestUtil;

    @Autowired
    public ReviewMapperImpl(ReviewRepository reviewRepository, MovieApiRequestUtil movieApiRequestUtil) {
        this.reviewRepository = reviewRepository;
        this.movieApiRequestUtil = movieApiRequestUtil;
    }

    @Override
    public ReviewInfo of(Review review) {
        if (review == null) return null;
        MovieDetail movieDetail = movieApiRequestUtil.requestMovieDetail(review.getReviewTargetId());

        return ReviewInfo.builder()
                .id(review.get_id().toHexString())
                .title(movieDetail.getMovieTitle())
                .imgPathUrl(movieDetail.getMoviePosterImgPath())
                .reviewContent(review.getReviewContent())
                .targetId(review.getReviewTargetId())
                .display(review.isDisplay())
                .regDate(review.getRegDate())
                .modTime(review.getModDate())
                .build();
    }

    @Override
    public List<ReviewInfo> of(List<Review> reviews) {

        if (CollectionUtils.isEmpty(reviews)) return null;

        List<ReviewInfo> reviewInfos = new ArrayList<>();
        for (Review review:reviews){
            if (review != null) reviewInfos.add(of(review));
        }

        return reviewInfos;
    }

}
