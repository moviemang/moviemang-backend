package com.moviemang.playlist.mapper;

import com.moviemang.datastore.entity.mongo.Review;
import com.moviemang.playlist.dto.ReviewInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReviewMapper {
    @Mapping(source = "_id", target = "id" )
    @Mapping(source = "reviewContent", target = "content")
    @Mapping(source = "reviewTargetId", target = "targetId")
    @Mapping(source = "reviewTargetType", target = "targetType")
    @Mapping(target = "targetTitle", ignore = true)
    @Mapping(target = "targetImgPathUrl", ignore = true)
    ReviewInfo of(Review review);

    List<ReviewInfo> of(List<Review> reviews);

}
