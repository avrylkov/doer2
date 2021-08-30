package com.example.doer.doer2.repositories;

import com.example.doer.doer2.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    @Query("from Admin a where a.login = :login and a.password = :password")
    List<Admin> getAdminByLoginAndPassword(String login,String password);
}
