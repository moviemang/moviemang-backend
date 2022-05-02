package com.moviemang.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moviemang.coreutils.model.vo.CommonParam;
import lombok.Data;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@ToString(callSuper = true)
public class DeletedMember extends CommonParam {
}
