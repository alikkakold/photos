package com.sergioburik.photos.controllers;

import com.sergioburik.photos.models.Post;
import com.sergioburik.photos.models.PostItem;
import com.sergioburik.photos.models.User;
import com.sergioburik.photos.repositories.PostRepository;
import com.sergioburik.photos.repositories.UserRepository;
import com.sergioburik.photos.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/p/{user_id}")
    public String profile(@PathVariable("user_id") String username, Model model) {
        User user = userRepository.findByUsername(username).get();
        model.addAttribute("profile", user);
        model.addAttribute("posts", postRepository.findAllByUserOrderByDateDesc(user));

        return "profile";
    }

    @GetMapping("/")
    public String main(Model model) {
        return "index";
    }

    @PostMapping("/new/post")
    public String addPost(@RequestParam String text,
                          @RequestParam MultipartFile image,
                          HttpServletResponse response) throws Exception {
        try {
            postService.save(text, image);
        } catch (Exception e) {
            return "redirect:/new/post?error";
        }

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(Model model) {
        List<Post> postList = postRepository.findAllByOrderByDateDesc();
        if (postList.size() >= 9) {
            model.addAttribute("posts", postList.subList(0, 9));
        } else {
            model.addAttribute("posts", postList);
        }

        return "search";
    }

    @GetMapping("/new/post")
    public String addPhoto() {
        return "new_photo";
    }
}
