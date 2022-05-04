package com.moviemang.datastore.repository.maria;


import com.moviemang.datastore.entity.maria.MailServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailUserRepository extends JpaRepository<MailServiceUser,Long> {

}
