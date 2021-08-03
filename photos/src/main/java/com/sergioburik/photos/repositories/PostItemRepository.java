package com.sergioburik.photos.repositories;

import com.sergioburik.photos.models.PostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItemRepository extends JpaRepository<PostItem, Long> {
}
