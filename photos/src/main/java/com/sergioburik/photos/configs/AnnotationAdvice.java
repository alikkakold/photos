package com.sergioburik.photos.configs;

import com.sergioburik.photos.models.User;
import com.sergioburik.photos.repositories.SubscriberRepository;
import com.sergioburik.photos.repositories.UserRepository;
import com.sergioburik.photos.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class AnnotationAdvice {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriberService subscriberService;

    @ModelAttribute("subscriberRepository")
    public SubscriberService subscriberService() {
        return subscriberService;
    }

    @ModelAttribute("user")
    public User user() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);

    }

}
