package com.moviemang.datastore.entity.mongo;

import com.google.common.collect.Lists;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Playlist extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;
    private String playlistTitle;
    private Long memberId;
    private String playlistDescription;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "tag")
    @JoinColumn(table = "tag")
    private List <Tag> tags= Lists.newArrayList();
    private List<Long> movieIds;
    private boolean display;

    public Integer calculateMovieCount(){
        return this.movieIds.size();
    }

}
