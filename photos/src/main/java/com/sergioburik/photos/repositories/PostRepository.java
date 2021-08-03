package com.sergioburik.photos.repositories;

import com.sergioburik.photos.models.Post;
import com.sergioburik.photos.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByDateAsc();
    List<Post> findAllByUserOrderByDateDesc(User user);
    List<Post> findAllByOrderByDateDesc();

    @Cacheable(value = "posts", key = "#user")
    List<Post> findAllByUser(User user);
}
