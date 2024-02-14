package com.sparta.team2newsfeed.entity;

import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Likes(UserDetailsImpl userDetails, Board board) {
        this.user = userDetails.getUser();
        this.board = board;
    }

}
