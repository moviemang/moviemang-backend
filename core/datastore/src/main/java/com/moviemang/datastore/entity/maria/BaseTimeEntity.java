package com.moviemang.datastore.entity.maria;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "reg_Date")
    @JsonProperty("mod_date")
    private LocalDateTime regDate; // 회원 등록일

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("mod_date")
    @Column(name = "mod_date")
    private LocalDateTime modDate; // 회원 수정일

    public LocalDateTime getCreatedDate() {
        return regDate;
    }

    public LocalDateTime getModifiedDate() {
        return modDate;
    }
}
