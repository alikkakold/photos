package com.sergioburik.photos.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = @Index(columnList = "id,user_id"))
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "post")
    private Set<PostItem> postItems = new HashSet<>();

    public Post() {
    }

    public Post(User user, String text, Date date) {
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PostItem> getPostItems() {
        return postItems;
    }

    public void setPostItems(Set<PostItem> postItems) {
        this.postItems = postItems;
    }
}
