package com.sparta.team2newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardUpdateRequestDto {

    private String title;

    private String body;

    @Pattern(regexp = "^(KOREAN|CHINESE|JAPANESE|WESTERN)$")
    private String category;

    @Pattern(regexp = "^[12345]$")
    private String cookLevel;
}
