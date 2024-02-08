package com.sparta.team2newsfeed.repository;

import com.sparta.team2newsfeed.dto.BoardResponseDto;
import com.sparta.team2newsfeed.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByCreatedAtDesc();
    List<Board> findBoardsByCategoryEqualsOrderByCreatedAtDesc(String uppenCategoryName);

    List<Board> findBoardsByCookLevelEqualsOrderByCreatedAtDesc(int cookLevel);
}
