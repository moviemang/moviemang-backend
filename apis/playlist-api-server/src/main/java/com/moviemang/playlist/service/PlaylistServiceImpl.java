package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import com.moviemang.datastore.entity.mongo.MovieInfo;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private LikeRepository likeRepository;
    private MemberRepository memberRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, LikeRepository likeRepository, MemberRepository memberRepository){
        this.playlistRepository = playlistRepository;
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public CommonResponse playlistOrderByLike() {
        Aggregation likeAggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("regDate").gte(LocalDate.now().minusDays(1))),
                Aggregation.lookup("like", "_id", "targetId", "likes")
        );

        List<PlaylistOrderByLikeDto> filterByTypeAndGroupByTargetId = playlistRepository.playlistOrderByLike(likeAggregation, "playlist")
                .getMappedResults()
                .stream()
                .map( playlistLikeJoin -> {
                    List<String> imgPathList = playlistLikeJoin.getMovies().stream().map(MovieInfo::getImaPath).collect(Collectors.toList());
                    return PlaylistOrderByLikeDto.builder()
                            ._id(playlistLikeJoin.get_id())
                            .playlistTitle(playlistLikeJoin.getPlaylistTitle())
                            .memberName(memberRepository.findByMemberId(playlistLikeJoin.getMemberId())
                                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND))
                                    .getMemberName())
                            .representativeImagePath(imgPathList)
                            .tags(playlistLikeJoin.getTags())
                            .movieCount(playlistLikeJoin.getMovies().size())
                            .likeCount(playlistLikeJoin.getLikes().size())
                            .build();
                })
                .sorted((o1, o2) -> Integer.compare(o2.getLikeCount(), o1.getLikeCount()))
                .limit(4)
                .collect(Collectors.toList());

        return CommonResponse.success(filterByTypeAndGroupByTargetId);
    }
}
