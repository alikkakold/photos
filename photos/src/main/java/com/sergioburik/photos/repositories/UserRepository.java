package com.sergioburik.photos.repositories;

import com.sergioburik.photos.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(value = "users", key = "#username")
    Optional<User> findByUsername(String username);

    @Cacheable
    List<User> findByUsernameContaining(String username);
}
