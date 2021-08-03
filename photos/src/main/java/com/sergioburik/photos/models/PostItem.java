package com.sergioburik.photos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class PostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    private String itemFileName;

    public PostItem() {
    }

    public PostItem(Post post, String itemFileName) {
        this.post = post;
        this.itemFileName = itemFileName;
    }

    public String getItemFileName() {
        return itemFileName;
    }

    public void setItemFileName(String itemFileName) {
        this.itemFileName = itemFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
