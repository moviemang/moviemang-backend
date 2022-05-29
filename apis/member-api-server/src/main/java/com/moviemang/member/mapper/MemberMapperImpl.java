package com.moviemang.member.mapper;

import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.dto.MemberInfo;
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
public class MemberMapperImpl implements MemberMapper {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberInfo of(Member member) {
        if (member == null) return  null;

        return MemberInfo.builder()
                .email(member.getMemberEmail())
                .id(member.getMemberId())
                .password(member.getMemberPassword())
                .build();
    }

    @Override
    public List<MemberInfo> of(List<Member> members) {
        if (CollectionUtils.isEmpty(members)) return null;

        List<MemberInfo> memberInfos = new ArrayList<>();
        for (Member member : members){
            if (member!=null)
                memberInfos.add(of(member));
        }

        return memberInfos;
    }
}
