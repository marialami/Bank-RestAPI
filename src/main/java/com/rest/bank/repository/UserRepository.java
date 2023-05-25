package com.rest.bank.repository;

import com.rest.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT a FROM User a WHERE a.document = :document and a.password =:password")
    User validateCredentials(@Param("document") int document, @Param("password") String password);

}
