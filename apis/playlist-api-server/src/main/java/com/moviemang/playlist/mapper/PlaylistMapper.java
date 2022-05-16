package com.moviemang.playlist.mapper;

import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.entity.mongo.Tag;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.dto.TagInfo;
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
public interface PlaylistMapper {

    @Mapping(source = "_id", target = "id" )
    @Mapping(source = "playlistTitle", target = "title")
    @Mapping(target = "movieCount", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "nickname", ignore = true)
    @Mapping(target = "representativeImagePath", ignore = true)
    PlaylistInfo of(Playlist playlist);
    List<PlaylistInfo> of(List<Playlist> playlists);

    @Mapping(source = "_id", target = "id" )
    @Mapping(source = "tagName", target = "name")
    TagInfo of(Tag tag);
}
