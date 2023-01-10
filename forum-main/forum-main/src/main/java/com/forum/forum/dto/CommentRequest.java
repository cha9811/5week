package com.forum.forum.dto;

import com.forum.forum.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    String content;

    public Comment toEntity(String username) {
        return Comment.builder().username(username).content(content).build();
    }
}
