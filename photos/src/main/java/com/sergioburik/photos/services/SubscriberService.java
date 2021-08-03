package com.sergioburik.photos.services;

import com.sergioburik.photos.models.Subscriber;
import com.sergioburik.photos.models.User;
import com.sergioburik.photos.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriberService {

    @Autowired
    SubscriberRepository subscriberRepository;

    public Subscriber save(User user, User subscriber) throws Exception {
        if (subscriberRepository.findByUserAndSubscriber(user, subscriber).isEmpty()) {
            Subscriber sub = new Subscriber(user, subscriber);
            return subscriberRepository.save(sub);

        } else {
            throw new Exception("Subscriber already exists.");
        }
    }

    public boolean ifUserIsSubscriber(User sub, User user) {
        return subscriberRepository.findByUserAndSubscriber(user, sub).isPresent();
    }

}
