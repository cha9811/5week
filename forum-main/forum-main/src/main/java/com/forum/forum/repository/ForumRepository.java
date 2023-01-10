package com.forum.forum.repository;

import com.forum.forum.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long>{
        List<Forum> findAllByOrderByModifiedAtDesc();

}
