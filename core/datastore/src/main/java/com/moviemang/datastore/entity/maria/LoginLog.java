package com.moviemang.datastore.entity.maria;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class LoginLog {
    @Id
    @GeneratedValue
    private Long seq;
    private Long memberId;
    private String ip;
    private String device;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "reg_date")
    @JsonProperty("reg_date")
    private LocalDateTime regDate; // 회원 등록일

}
