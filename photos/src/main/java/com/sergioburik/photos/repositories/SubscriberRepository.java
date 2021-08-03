package com.sergioburik.photos.repositories;

import com.sergioburik.photos.models.Subscriber;
import com.sergioburik.photos.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByUserAndSubscriber(User user, User subscriber);

    List<Subscriber> findAllByUser(User user);

    @Cacheable(value = "subsriber", key = "#user_id")
    List<Subscriber> findAllBySubscriberId(Long user);

}
