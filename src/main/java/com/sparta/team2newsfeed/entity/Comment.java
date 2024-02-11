package com.sparta.team2newsfeed.entity;

import com.sparta.team2newsfeed.dto.AddCommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends Timestemped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //comment repository 저장시 사용
    private Comment(AddCommentRequestDto addCommentRequestDto, Board board, User user) {
        this.id = addCommentRequestDto.getId();
        this.comment = addCommentRequestDto.getComment();
        this.board = board;
        this.user = user;
    }

}
