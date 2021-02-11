package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {
    Post post;
    @BeforeEach
    void setUp(){
        UserEntity userEntity = getUser();
        post = new Post(
                "Ogłoszenie",
                "Zrzuta na autobus",
                LocalDateTime.parse("2021-03-13T10:15:30"),
                userEntity
        );
    }



    @Test
    void shouldHasZeroLikesAndDislikes(){
        assertEquals(0,post.numberOfLikes,"value correct");
        assertEquals(0,post.numberOfDislikes,"value correct");
    }

    @Test
    void shouldAddLike(){
        post.addLike(1L);
        assertEquals(1,post.numberOfLikes,"value correct");
        assertEquals(0,post.numberOfDislikes,"value correct");
    }

    @Test
    void shouldDislike(){
        post.addDislike(1L);
        assertEquals(0, post.numberOfLikes,"value correct");
        assertEquals(1, post.numberOfDislikes,"value correct");
    }

    @Test
    void shouldRemoveLikeAfterDoubleLike(){
        post.addLike(1L);
        assertEquals(1,post.numberOfLikes,"value correct");
        post.addLike(1L);
        assertEquals(0,post.numberOfLikes,"value correct");
        assertEquals(0,post.numberOfDislikes,"value correct");

    }

    @Test
    void shouldRemoveDisLkeAfterDoubleDislike(){
        post.addDislike(1L);
        assertEquals(1, post.numberOfDislikes);
        post.addDislike(1L);
        assertEquals(0,post.numberOfLikes,"value correct");
        assertEquals(0,post.numberOfDislikes,"value correct");
    }

    @Test
    void shouldRemoveLikeAndAddDislike(){
        post.addLike(1L);
        assertEquals(1, post.getNumberOfLikes());
        assertEquals(0, post.getNumberOfDislikes());
        post.addDislike(1L);
        assertEquals(0,post.numberOfLikes,"value correct");
        assertEquals(1,post.numberOfDislikes,"value correct");
    }

    @Test
    void shouldRemoveDislikeAndAddLike(){
        post.addDislike(1L);
        assertEquals(0, post.getNumberOfLikes());
        assertEquals(1, post.getNumberOfDislikes());
        post.addLike(1L);
        assertEquals(1,post.numberOfLikes,"value correct");
        assertEquals(0,post.numberOfDislikes,"value correct");
    }

    private UserEntity getUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        return userEntity;
    }

}
