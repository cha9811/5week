package com.forum.forum.entity;

import com.forum.forum.dto.ForumRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Forum extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forum_id")
    private Long id;

    @Column(nullable = false)   //닉네임
    private String username;

    @ManyToOne(fetch = LAZY)    //작성자명
    @JoinColumn(name = "writer_id")
    private User writer;
    @Column(nullable =false)    //제목
    private String title;

    @Column(nullable = false)   //내용
    private String content;

    /* 게시글을 삭제하면 달려있는 댓글 모두 삭제*/
    @OneToMany(mappedBy = "forum", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();



    public void update(ForumRequest forumRequest) {
        this.title = forumRequest.getTitle();
        this.content=forumRequest.getContent();
    }

    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(User writer) {
        //writer는 변경이 불가능하므로 이렇게만 해주어도 될듯
        this.writer = writer;
        writer.addForum(this);
    }

    public void addComment(Comment comment){
        //comment의 Post 설정은 comment에서 함
        commentList.add(comment);
    }

    @Builder
    public Forum(String username, String title, String content) {
        this.username=username;
        this.title = title;
        this.content = content;
    }

}

