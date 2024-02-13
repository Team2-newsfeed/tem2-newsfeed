package com.sparta.team2newsfeed.dto;

import com.sparta.team2newsfeed.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AddBoardRequestDto {

    private Long id;

    private String title;

    private String body;

    @Pattern(regexp = "^[CHINESE|KOREAN|JAPANESE|WESTERN]$")
    private String category;

    @Pattern(regexp = "^[1|2|3|4|5]$")
    private String cookLevel;

    private User user;

}