package com.moviemang.member.service;

import com.moviemang.datastore.entity.maria.MailServiceUser;

public interface MailUserService {
    public void memberJoin(MailServiceUser mailServiceUser);
}
