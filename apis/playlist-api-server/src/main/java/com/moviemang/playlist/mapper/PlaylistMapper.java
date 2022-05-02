package com.moviemang.playlist.mapper;

import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.entity.mongo.Tag;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.dto.TagInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PlaylistMapper {
    PlaylistInfo of(Playlist playlist);
    List<PlaylistInfo> of(List<Playlist> playlists);
    TagInfo of(Tag tag);
}
