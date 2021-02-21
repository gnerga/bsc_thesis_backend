package com.nerga.travelCreatorApp.security.auth.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    List<UserEntity>  findAllByUsernameIsNot(String username);
    List<UserEntity>  findAllByUsernameIsNotAndUsernameContains(
            String username,
            String text);
    List<UserEntity>  findAllByUsernameIsNotAndFirstNameContains(
            String username,
            String text);
    List<UserEntity> findAllByUsernameIsNotAndLastNameContains(
            String username,
            String text);
}
