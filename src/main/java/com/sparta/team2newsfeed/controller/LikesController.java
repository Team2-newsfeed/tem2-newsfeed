package com.sparta.team2newsfeed.controller;

import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.service.LikesService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class LikesController {
    private final LikesService likesService;

    public LikesController(LikesService likeService) {
        this.likesService = likeService;
    }

    @PostMapping("/{boardId}/likes")
    private ResponseEntity<StatusResponseDto> addLikes(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @PathVariable Long boardId) {
        return likesService.addLikes(userDetails, boardId);
    }
}
