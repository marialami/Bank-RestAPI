package com.rest.bank.repository;

import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> {

}
