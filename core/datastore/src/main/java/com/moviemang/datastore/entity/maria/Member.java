package com.moviemang.datastore.entity.maria;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@Entity
public class Member {
    @Id
    private Long id;

}
