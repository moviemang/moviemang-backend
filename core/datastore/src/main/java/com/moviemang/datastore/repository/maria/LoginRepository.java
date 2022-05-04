package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.LoginLog;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<LoginLog, Long> {
}
