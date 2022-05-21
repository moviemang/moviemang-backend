package com.moviemang.datastore.dto.member;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NameCheckDto {

    @Length(min = 4,max = 20,message = "닉네임은 4-20자 입니다.")
    String nickname;
}
