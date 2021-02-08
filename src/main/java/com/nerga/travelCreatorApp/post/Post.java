package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.post.like.Dislike;
import com.nerga.travelCreatorApp.post.like.Like;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import io.vavr.Tuple2;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    String title;
    String content;

    @ManyToOne
    PostManager postManager;

    LocalDateTime timeStamp;

    @ManyToOne
    UserEntity author;

    int numberOfLikes;
    int numberOfDislikes;

    @OneToMany
    List<Like> likes;

    @OneToMany
    List<Dislike> dislike;

    public Post(String title, String content, LocalDateTime timeStamp, UserEntity author) {

        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.author = author;

        this.numberOfDislikes = 0;
        this.numberOfLikes = 0;

        this.likes = new ArrayList<>();
        this.dislike = new ArrayList<>();

    }
}
