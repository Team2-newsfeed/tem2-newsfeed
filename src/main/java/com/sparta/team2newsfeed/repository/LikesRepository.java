package com.sparta.team2newsfeed.repository;

import com.sparta.team2newsfeed.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {
    Likes findLikesByBoard_IdAndUser_IdEquals(Long boardId, Long userId);

    Optional<Likes> findAllByBoard_Id(Long boardId);

}
