package com.sparta.team2newsfeed.controller;

import com.sparta.team2newsfeed.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    //Service 주입
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public RequestEntity<?> userSignup(@Valid @RequestBody #DTO) {
        return userService.userSignup(#DTO);

    }

    //로그인
    @PostMapping("/login")
    public RequestEntity<?> userLogin(@AuthenticationPrincipal UserDetarilsImpl userDetarils,
                                      #DTO) {
        return userService.userLogin(userDetarils, #DTO);
    }

    //로그아웃
    @PostMapping("/logout")
    public void userLogout(@AuthenticationPrincipal UserDetarilsImpl userDetarils) {
        userService.userLogout(userDetarils);
    }

    //회원수정
    @PutMapping("/{boardId}")
    public RequestEntity<?> userUpdate(@PathVariable Long boardId,
                                       @AuthenticationPrincipal UserDetarilsImpl userDetarils,
                                       #DTO
                                       ) {
        return userService.userUpdate(boardId, userDetarils, #DTO);

    }

}
