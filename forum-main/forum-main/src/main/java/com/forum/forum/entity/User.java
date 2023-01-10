package com.forum.forum.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy="writer")
    private List<Forum> forumList = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Comment> commentList = new ArrayList<>();

    /* 연관관계 메서드 */
    public void addForum(Forum forum){
    /*forum의  writer 설정은 forum에서 함*/
        forumList.add(forum);
    }

    public void addComment(Comment comment){
    /*comment의 writer 설정은 comment에서 함*/
        commentList.add(comment);
    }
}
