package com.nerga.travelCreatorApp.post.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    String title;
    String content;
    String timeStamp;
    Long userId;

}
