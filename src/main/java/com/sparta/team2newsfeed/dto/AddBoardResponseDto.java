package com.sparta.team2newsfeed.dto;

import com.sparta.team2newsfeed.entity.Board;
import com.sparta.team2newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddBoardResponseDto {

    private Long id;

    private String title;

    private String body;

    private String category;

    private String username;

    private LocalDateTime createdAt;

    public AddBoardResponseDto(Board board, User user) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.body = board.getBody();
        this.category = board.getCategory();
        this.username = user.getUsername();
        this.createdAt = board.getCreatedAt();
    }
}