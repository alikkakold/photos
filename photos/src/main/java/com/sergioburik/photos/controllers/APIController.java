package com.sergioburik.photos.controllers;

import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;
import com.sergioburik.photos.models.Post;
import com.sergioburik.photos.models.Subscriber;
import com.sergioburik.photos.models.User;
import com.sergioburik.photos.repositories.PostRepository;
import com.sergioburik.photos.repositories.SubscriberRepository;
import com.sergioburik.photos.repositories.UserRepository;
import com.sergioburik.photos.services.PostItemService;
import com.sergioburik.photos.services.PostService;
import com.sergioburik.photos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.expression.Lists;

import java.util.*;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    PostItemService postItemService;

    @Autowired
    SubscriberRepository subscriberRepository;

    @Value("${upload.path}")
    String uploadPath;


    @GetMapping("/users/{id}")
    public User user(@PathVariable("id") Long id) {
        return userRepository.getOne(id);
    }

    @GetMapping("/users")
    public List<User> users(@RequestParam(name = "username", required = false) Optional<String> username) {

        if (username.isPresent()) {
            return userRepository.findByUsernameContaining(username.get());
        }


        return userRepository.findAll();

    }

    @PostMapping("/subscribers")
    public Subscriber addSubscriber(@RequestParam Long subId, @RequestParam Long userId) throws Exception {
        User sub = userRepository.getOne(subId);
        User user = userRepository.getOne(userId);

        if (subscriberRepository.findByUserAndSubscriber(user, sub).isPresent()) {
            throw new Exception("Subscriber already exists");
        }

        Subscriber subscriber = new Subscriber(user, sub);
        return subscriberRepository.save(subscriber);
    }

    @PostMapping("/posts")
    public Post addPost(@RequestParam String text,
                        @RequestParam MultipartFile image) throws Exception {

        return postService.save(text, image);
    }

    @GetMapping("/users/{id}/feed")
    public List<Post> feed(@PathVariable Long id) {
        // find all entities where the user is a subscriber
        List<Subscriber> subscribers = subscriberRepository.findAllBySubscriberId(id);

        // result list
        List<Post> posts = new ArrayList<>();

        for (Subscriber subscriber : subscribers) {
            posts.addAll(postRepository.findAllByUser(subscriber.getUser()));
        }

        if (posts.size() > 0) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(final Post object1, final Post object2) {
                    return object2.getDate().compareTo(object1.getDate());
                }
            });
        }

        return posts;
    }


}
