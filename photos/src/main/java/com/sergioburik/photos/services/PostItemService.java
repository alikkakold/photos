package com.sergioburik.photos.services;

import com.sergioburik.photos.models.Post;
import com.sergioburik.photos.models.PostItem;
import com.sergioburik.photos.models.UserRole;
import com.sergioburik.photos.repositories.PostItemRepository;
import com.sergioburik.photos.tools.ImageCrop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class PostItemService {

    @Autowired
    PostItemRepository postItemRepository;

    @Value("${upload.path}")
    String uploadPath;

    @Autowired
    ImageCrop imageCrop;

    public PostItem save(MultipartFile file, Post post) throws Exception {
        PostItem postItem = new PostItem();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String resultFilename = imageCrop.saveSquareImage(file);

            postItem.setPost(post);
            postItem.setItemFileName(resultFilename);
        } else {
            throw new Exception("Unable to save photo");
        }

        return postItemRepository.save(postItem);
    }

}
