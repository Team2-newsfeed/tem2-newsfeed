package com.sparta.team2newsfeed.dto;

import com.sparta.team2newsfeed.entity.Comment;
import com.sparta.team2newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;

    private String comment;

    private Long boardId;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    //유저한테 전달할 정보 생성
    public CommentResponseDto(Comment comment, Long boardId, User user) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.boardId = boardId;
        this.username = user.getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
