package com.moviemang.playlist.mapper;

import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.entity.mongo.Tag;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.dto.TagInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2022-05-01T21:03:36+0900",
        comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 11.0.14.1 (JetBrains s.r.o.)"
)
@Component
public class PlaylistMapperImpl implements PlaylistMapper {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public PlaylistInfo of(Playlist playlist) {
        if (playlist == null) return null;

        return PlaylistInfo.builder()
                .id(playlist.get_id().toHexString())
                .title(playlist.getPlaylistTitle())
                .tags(tagInfos(playlist.getTags()))
                .nickname(memberRepository.findByMemberId(playlist.getMemberId()).get().getMemberName())
                .movieCount(CollectionUtils.isEmpty(playlist.getMovieIds())?0:playlist.getMovieIds().size())
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
        if (CollectionUtils.isNotEmpty(tags)){
            for (Tag tag : tags){
                tagInfos.add(this.of(tag));
            }
        }
        return tagInfos;
    }

    @Override
    public TagInfo of(Tag tag) {
        if (tag == null) return null;
        TagInfo.TagInfoBuilder tagInfo = TagInfo.builder();
        tagInfo.id(tag.get_id());
        tagInfo.name(tag.getTagName());

        return tagInfo.build();
    }
}
