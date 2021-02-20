package com.nerga.travelCreatorApp.post.dto;

import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PostDetailsDto {

    Long postId;
    String title;
    String content;
    LocalDateTime timeStamp;
    UserDetailsDto author;
    int numberOfLikes;
    int getNumberOfLikes;
    List<Long> likes;
    List<Long> dislikes;

}
