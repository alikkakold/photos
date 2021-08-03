package com.sergioburik.photos.services;

import com.sergioburik.photos.models.Post;
import com.sergioburik.photos.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Component
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostItemService postItemService;

    @Transactional
    public Post save(String text, MultipartFile image) throws Exception {
        Post post = new Post(userService.getCurrentUser(), text, new Date());

        Post post_ = null;
        try {
            post_ = postRepository.save(post);
            postItemService.save(image, post);
        } catch (Exception e) {
            postRepository.delete(post_);
        }

        return post;
    }


}
