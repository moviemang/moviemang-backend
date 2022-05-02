package com.moviemang.playlist.mapper;

import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.entity.mongo.Tag;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.dto.TagInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaylistMapperImpl implements PlaylistMapper {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public PlaylistInfo of(Playlist playlist) {
        if (playlist == null) return null;

        return PlaylistInfo.builder()
                .id(playlist.getPlaylistId())
                .title(playlist.getPlaylistTitle())
                .tags(tagInfos(playlist.getTags()))
                .nickname(memberRepository.findByMemberId(playlist.getMemberId()).getMemberName())
                .movieCount(playlist.calculateMovieCount())
                .build();
    }

    @Override
    public List<PlaylistInfo> of(List<Playlist> playlists) {

        if (CollectionUtils.isEmpty(playlists)) return null;

        List<PlaylistInfo> playlistInfos = new ArrayList<>();
        for (Playlist playlist:playlists){
            if (playlist != null) playlistInfos.add(of(playlist));
        }

        return playlistInfos;
    }

    protected List<TagInfo> tagInfos(List<Tag> tags){
        List<TagInfo> tagInfos = new ArrayList<>();
        for (Tag tag : tags){
            tagInfos.add(this.of(tag));
        }
        return tagInfos;
    }

    @Override
    public TagInfo of(Tag tag) {
        if (tag == null) return null;
        TagInfo.TagInfoBuilder tagInfo = TagInfo.builder();
        tagInfo.id(tag.getId());
        tagInfo.name(tag.getName());

        return tagInfo.build();
    }
}
