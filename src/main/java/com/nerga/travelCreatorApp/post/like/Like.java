package com.nerga.travelCreatorApp.post.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long likeId;
    long userId;
    boolean isLiked;

}
