package com.moviemang.datastore.entity.maria;

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
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate; // 회원 등록일

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate; // 회원 수정일

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public LocalDateTime getModDate() {
        return modDate;
    }
}
