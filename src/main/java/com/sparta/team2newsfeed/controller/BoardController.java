package com.sparta.team2newsfeed.controller;

import com.sparta.team2newsfeed.dto.BoardRequestDto;
import com.sparta.team2newsfeed.dto.BoardUpdateRequestDto;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BoardController {
    //Service 주입
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //전체게시글 조회
    @GetMapping("/board")
    public ResponseEntity<?> getBoardAll() {
        return boardService.getBoardAll();
    }

    //단일게시글 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoardOne(@PathVariable Long boardId) {
        return boardService.getBoardOne(boardId);
    }

    //카테고리별 조회
    @GetMapping("/board/category/{categoryName}")
    public ResponseEntity<?> getBoardCategory(@PathVariable String categoryName) {
        return boardService.getBoardCategory(categoryName);
    }

    //난이도별 조회
    @GetMapping("/board/cooklevel/{cookLevel}")
    public ResponseEntity<?> getBoardCookLevel(@PathVariable int cookLevel) {
        return boardService.getBoardCookLevel(cookLevel);
    }

    //게시글 작성
    @PostMapping("/boardmake")
    public ResponseEntity<?> addBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody BoardRequestDto boardRequestDto
    ) {
        return boardService.addBoard(userDetails, boardRequestDto);

    }

    //게시글 수정
    @PutMapping("/boardmake/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody BoardUpdateRequestDto boardUpdateRequestDto
    ) {
        return boardService.updateBoard(boardId, userDetails, boardUpdateRequestDto);
    }

    //게시글 삭제
    @DeleteMapping("/boardmake/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return boardService.deleteBoard(boardId, userDetails);
    }
}
