package com.sparta.team2newsfeed.repository;

import com.sparta.team2newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard_IdOrderByCreatedAt(Long boardId);
}
