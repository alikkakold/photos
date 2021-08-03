package com.sergioburik.photos.controllers;

import com.sergioburik.photos.models.User;
import com.sergioburik.photos.models.UserRole;
import com.sergioburik.photos.repositories.UserRepository;
import com.sergioburik.photos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam("avatar") MultipartFile avatar,
                          @RequestParam String first_name, @RequestParam String last_name,
                          @RequestParam String password, Model model) throws Exception {

        try {
            User u = userService.save(username, first_name,
                    last_name, "", password, avatar);
        } catch (Exception e) {
            return "redirect:/registration?error";
        }

        return "redirect:/login";

    }


}
