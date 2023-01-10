package com.forum.forum.entity;

import com.forum.forum.dto.CommentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;        //댓글 구분용


    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;     //댓글 작성자


    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="forum_id")
    private Forum forum;      //제목


    @Column
    private String content;     //내용

    @Column
    private String username;    //아이디

    /* 연관관계 편의 메서드 */
    public void confirmWriter(User writer) {
        this.writer = writer;
        writer.addComment(this);
    }

    public void confirmForum(Forum forum) {
        this.forum = forum;
        forum.addComment(this);
    }

    public void updateComment(CommentRequest commentRequest) {
        this.content= commentRequest.getContent();
    }
    @Builder
    public Comment( User writer, Forum forum, String username, String content) {
        this.writer = writer;
        this.forum = forum;
        this.username = username;
        this.content = content;

    }
}
