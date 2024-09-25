package com.lms.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserID(Long userID);
}
