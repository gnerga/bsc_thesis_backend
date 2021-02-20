package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.expensesregister.Expenses;
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
public class Post implements Comparable<Post>{

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
    List<Long> dislikes;

    public Post(String title, String content, LocalDateTime timeStamp, UserEntity author) {

        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.author = author;

        this.numberOfDislikes = 0;
        this.numberOfLikes = 0;

        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();

    }

    public Post() {

    }

    public void addLike(Long like) {
        if (likes.contains(like)) {
            likes.remove(like);
            numberOfLikes = likes.size();
        } else if (dislikes.contains(like)){
            dislikes.remove(like);
            likes.add(like);
            numberOfLikes = likes.size();
            numberOfDislikes = dislikes.size();
        } else {
            likes.add(like);
            numberOfLikes = likes.size();
        }
    }

    public void addDislike(Long dislike){
        if(dislikes.contains(dislike)){
            dislikes.remove(dislike);
            numberOfDislikes = dislikes.size();
        } else if (likes.contains(dislike)){
            likes.remove(dislike);
            dislikes.add(dislike);
            numberOfDislikes = dislikes.size();
            numberOfLikes = likes.size();
        } else {
            dislikes.add(dislike);
            numberOfDislikes = dislikes.size();
        }
    }

    @Override
    public int hashCode() {
        return postId == null ? 0 : postId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(postId == null || obj == null || getClass() != obj.getClass())
            return false;

        Post that = (Post) obj;
        return postId.equals(that.postId);
    }


    @Override
    public int compareTo(Post o) {
        return this.getTimeStamp().compareTo(o.getTimeStamp());
    }
}
