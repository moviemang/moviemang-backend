package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.LoginLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<LoginLog, Long> {
}
