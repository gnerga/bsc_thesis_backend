package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.expensesregister.Expenses;
import com.nerga.travelCreatorApp.post.like.Dislike;
import com.nerga.travelCreatorApp.post.like.Like;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.trip.Trip;
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
    Trip trip;

    LocalDateTime timeStamp;

    @ManyToOne
    UserEntity author;

    int numberOfLikes;
    int numberOfDislikes;

    @ElementCollection
    List<Long> likes;

    @ElementCollection
    List<Long> dislike;

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

    public Post() {

    }

    public void addLike(Long like){
        likes.add(like);
    }
    public void removeLike(Long like){
        likes.remove(like);
    }
    public void addDislike(Long dislike) {likes.add(dislike);}
    public void removeDislike(Long dislike) {likes.remove(dislike);}


}
