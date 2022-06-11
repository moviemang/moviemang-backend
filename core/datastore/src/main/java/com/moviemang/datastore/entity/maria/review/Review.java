package com.moviemang.datastore.entity.maria.review;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import com.moviemang.datastore.entity.maria.Member;
import lombok.*;

import javax.persistence.*;


@Entity(name = "review")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "member_id")
    private Long memberId;

    private String content;

    @Column(name = "movie_id")
    private Long movieId;

    @JsonProperty(value = "movie_type")
    @Column(name = "movie_type")
    private String movieType;

    private boolean display;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Review(Long reviewId, Long memberId, String content, Long movieId, String movieType, boolean display) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.content = content;
        this.movieId = movieId;
        this.movieType = movieType;
        this.display = display;
    }
}
