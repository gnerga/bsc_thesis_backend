package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import java.time.LocalDateTime;

public class Comment {

    long commentId;
    UserEntity author;
    LocalDateTime timeStamp;

}
