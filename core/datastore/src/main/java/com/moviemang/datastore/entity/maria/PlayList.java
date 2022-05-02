package com.moviemang.datastore.entity.maria;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayList {


    private Long id;

    @Id
    public Long getId() {
        return id;
    }
}
