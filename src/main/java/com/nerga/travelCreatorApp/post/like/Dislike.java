package com.nerga.travelCreatorApp.post.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@Table(name="dislike")
public class Dislike extends Like{
}
