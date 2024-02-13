package com.sparta.team2newsfeed.dto;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPassCheckRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;


}
