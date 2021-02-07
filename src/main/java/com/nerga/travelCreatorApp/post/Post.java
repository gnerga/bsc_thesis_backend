package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import io.vavr.Tuple2;

import java.time.LocalDateTime;
import java.util.List;

public class Post {

    Long postId;
    String title;
    String content;

    LocalDateTime timeStamp;
    UserEntity author;
    int numberOfLikes;
    int numberOfDislikes;

    List<Tuple2<Integer, String>> likes;
    List<Tuple2<Integer, String>> dislike;

}
