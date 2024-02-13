package com.sparta.team2newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponseDto {
    //API 응답할때 쓰는 DTO

    private String msg; // 메세지
    private Integer statusCode; // 응답코드
}