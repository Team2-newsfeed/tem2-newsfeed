package com.sparta.team2newsfeed.controller;

import com.sparta.team2newsfeed.dto.StatusResponseDto;
import com.sparta.team2newsfeed.dto.UserRequestDto;
import com.sparta.team2newsfeed.dto.UserUpdateRequestDto;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.jwt.JwtUtil;
import com.sparta.team2newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    //Service 주입
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.userSignup(userRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(201).body(new StatusResponseDto("회원가입 성공", 201));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        try {
            userService.userLogin(userRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getUsername()));

        return ResponseEntity.ok().body(new StatusResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    //회원수정
    @PutMapping("/userupdate")
    public ResponseEntity<StatusResponseDto> userUpdate(@RequestBody UserUpdateRequestDto userUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userService.userUpdate(userUpdateRequestDto, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new StatusResponseDto("회원정보 수정 성공", HttpStatus.OK.value()));
    }

    //회원삭제
    @DeleteMapping("/userdelete")
    public ResponseEntity<StatusResponseDto> userDelete(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.userDelete(userUpdateRequestDto);
    }

}
