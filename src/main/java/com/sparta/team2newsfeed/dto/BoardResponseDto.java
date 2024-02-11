package com.sparta.team2newsfeed.dto;

import com.sparta.team2newsfeed.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String body;
    private String category;
    private String username;
    private Long likes;
    private Long comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.body = board.getBody();
        this.category = board.getCategory();
        this.username = board.getUser().getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.likes = board.getLikes().stream().count();
        this.comments = board.getComments().stream().count();
    }
}
