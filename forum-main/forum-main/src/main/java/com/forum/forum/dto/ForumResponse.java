package com.forum.forum.dto;

import com.forum.forum.entity.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ForumResponse {
    private LocalDateTime createdAt;    //만든시간

    private LocalDateTime modifiedAt;   //수정시간
    private String username;    //유저ID
    private String title;   //제목
    private String content; //내용
    private  List<CommentResponse> commentList; //댓글 리스트

    public ForumResponse(Forum forum){  //값 받기
        this.createdAt = forum.getCreatedAt();
        this.username = forum.getUsername();
        this.title = forum.getTitle();
        this.content = forum.getContent();
        this.modifiedAt = forum.getModifiedAt();
        commentList=new ArrayList<>();
    }
}
