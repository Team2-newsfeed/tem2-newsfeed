package com.sparta.team2newsfeed.controller;

import com.sparta.team2newsfeed.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    //Service 주입
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //전체게시글 조회
    @GetMapping
    public ResponseEntity<?> getBoardAll() {
        return boardService.getBoardAll();
    }

    //단일게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardOne(@PathVariable Long boardId) {
        return boardService.getBoardOne(boardId);
    }

    //카테고리별 조회
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getBoardCategory(@PathVariable String categoryName) {
        return boardService.getBoardCategory(categoryName);
    }

    //난이도별 조회
    @GetMapping("/cookLevel/{cookLevel}")
    public ResponseEntity<?> getBoardCookLevel(@PathVariable int cookLevel) {
        return boardService.getBoardCookLevel(cookLevel);
    }

    //게시글 작성
    @PostMapping
    public ResponseEntity<?> addBoard(
//            @AuthenticationPrincipal UserDetarilsImpl userDetarils,
//                                      @RequestBody #DTO
    ) {
        return boardService.addBoard(
//                userDetarils, #DTO
        );

    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId
//                                         ,@AuthenticationPrincipal UserDetarilsImpl userDetarils,
//                                         @RequestBody #DTO
    ) {
        return boardService.updateBoard(boardId
//                , userDetarils, #DTO
        );
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId
//            ,@AuthenticationPrincipal UserDetarilsImpl userDetarils,
                                         ) {
        return boardService.deleteBoard(boardId
//                , userDetarils
                );
    }
}
