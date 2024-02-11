package com.sparta.team2newsfeed.controller;

import com.sparta.team2newsfeed.dto.CommentRequestDto;
import com.sparta.team2newsfeed.dto.StatusResponseDto;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/{boardId}/comment")
public class CommentController {
    //comment service 주입
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //comment 등록
    @PostMapping
    private ResponseEntity<?> addComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody CommentRequestDto commentRequestDto,
                                         @PathVariable Long boardId) {
        return commentService.addComment(userDetails, commentRequestDto, boardId);
    }


    //comment 수정
    @PutMapping("/{commentId}")
    private ResponseEntity<?> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody CommentRequestDto CommentRequestDto,
                                            @PathVariable Long boardId,
                                            @PathVariable Long commentId) {
        return commentService.updateComment(userDetails, CommentRequestDto, boardId, commentId);
    }

    //comment 삭제
    @DeleteMapping("/{commentId}")
    private ResponseEntity<StatusResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long boardId,
                                                            @PathVariable Long commentId) {
        return commentService.deleteComment(userDetails, boardId, commentId);
    }

}
