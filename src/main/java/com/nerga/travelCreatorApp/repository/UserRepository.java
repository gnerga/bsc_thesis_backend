package com.nerga.travelCreatorApp.repository;

import com.nerga.travelCreatorApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserId(Long userId);
    Optional<User> findUserByUserLogin(String login);

    boolean existsByEmail(String email);
    boolean existsByUserId(Long id);
    boolean existsByUserLogin(String login);

}
