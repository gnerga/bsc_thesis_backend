package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.expensesregister.Expenses;
import com.nerga.travelCreatorApp.trip.Trip;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="PostManager")
@Entity

public class PostManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long postManagerId;

    @OneToMany
    List<Post> postList;

    public PostManager(){
        postList = new ArrayList<>();
    }

    public void addPost(Post post){
        postList.add(post);
    }

    public void removePost(Post post){
        postList.remove(post);
    }

    public Post findPost(int expenseId){
        return postList
                .stream()
                .filter(element -> element.postId == expenseId)
                .findFirst().orElse(null);
    }

}
