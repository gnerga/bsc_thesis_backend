package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public class Post {

    Long postId;
    String title;
    String content;
    LocalDateTime timeStamp;
    UserEntity author;
    int like;
    int dislike;
    List<Comment> comments;
    boolean isSendNotification;
    // todo dodać zdjęcie

}
