package com.forum.forum.dto;

import com.forum.forum.entity.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForumRequest {
    private String title;
    private String content;
    public Forum toEntity(String username) {
        return Forum.builder().title(title).content(content).username(username).build();
    }
}
