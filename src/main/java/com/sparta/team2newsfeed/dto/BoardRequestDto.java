package com.sparta.team2newsfeed.dto;

import com.sparta.team2newsfeed.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private Long id;

    private String title;

    private String body;

    @Pattern(regexp = "^(KOREAN|CHINESE|JAPANESE|WESTERN)$")
    private String category;

    @Pattern(regexp = "^[12345]$")
    private String cookLevel;

    private User user;
}
