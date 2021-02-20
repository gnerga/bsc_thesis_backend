package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.post.dto.PostDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
    void shouldSortListOfPost(){
        Post post = new Post(
                "Ogłoszenie",
                "Zrzuta na autobus",
                LocalDateTime.parse("2021-03-13T10:15:30"),
                null);

        Post post2 = new Post(
                "Ogłoszenie",
                "Zrzuta na autobus",
                LocalDateTime.parse("2021-03-13T11:15:30"),
                null);

        Post post5 = new Post(
                "Ogłoszenie",
                "Zrzuta na autobus",
                LocalDateTime.parse("2021-03-13T11:14:30"),
                null);

        Post post3 = new Post(
                "Ogłoszenie",
                "Zrzuta na autobus",
                LocalDateTime.parse("2021-02-13T10:15:30"),
                null);

        Post post4 = new Post(
                "Ogłoszenie",
                "Zrzuta na autobus",
                LocalDateTime.parse("2021-02-13T09:15:30"),
                null);

        List<Post> posts = new ArrayList<>();
        posts.add(post);
        posts.add(post2);
        posts.add(post5);
        posts.add(post3);
        posts.add(post4);

//        Collections.sort(posts);
//        Collections.reverse(posts);

        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getTimeStamp))
                .collect(Collectors.toList());

       for (Post it: sortedPosts) {
           System.out.println(it.timeStamp);
       }
        List<PostDetailsDto> list = new ArrayList<>();
        for (Post it: sortedPosts){
            list.add(new PostDetailsDto(
                    it.getPostId(),
                    it.getTitle(),
                    it.getContent(),
                    it.getTimeStamp(),
                    null,
                    it.getNumberOfLikes(),
                    it.getNumberOfDislikes(),
                    it.getLikes(),
                    it.getDislikes()

            ));
        }
        System.out.println();
        for (PostDetailsDto it: list){
            System.out.println(it.getTimeStamp());
        }

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
