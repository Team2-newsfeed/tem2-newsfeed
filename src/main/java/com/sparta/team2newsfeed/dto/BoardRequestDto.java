package com.sparta.team2newsfeed.dto;

import com.sparta.team2newsfeed.entity.User;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private Long id;

    private String title;

    private String body;

    private String category;

    private int cookLevel;

    private User user;
}
