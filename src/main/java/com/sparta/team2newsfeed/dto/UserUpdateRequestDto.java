package com.sparta.team2newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String nowPassword;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String newPassword;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String newCheckPassword;

    @Email
    private String email;

    private String intro;
}
