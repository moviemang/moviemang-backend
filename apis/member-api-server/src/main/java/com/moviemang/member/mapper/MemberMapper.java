package com.moviemang.member.mapper;

import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.dto.MemberInfo;
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
public interface MemberMapper {

    @Mapping(source = "memebrId", target = "id" )
    @Mapping(source = "memberEmail", target = "email")
    @Mapping(source = "memberName", target = "name")
    @Mapping(source = "memberPassword", target = "password")
    MemberInfo of(Member member);
    List<MemberInfo> of(List<Member> playlists);
}
