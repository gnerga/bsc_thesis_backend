package com.nerga.travelCreatorApp.post;

import com.nerga.travelCreatorApp.trip.Trip;

import javax.persistence.*;
import java.util.List;

@Table(name="PostManager")
@Entity
public class PostManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long postManagerId;

    @OneToMany
    List<Post> postList;

}
