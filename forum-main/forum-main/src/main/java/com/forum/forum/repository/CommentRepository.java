package com.forum.forum.repository;

import com.forum.forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByForum_IdOrderByModifiedAtDesc(Long forum_id);
    Optional<Comment> findCommentByForum_IdAndId(Long id, Long comment_id);
}
