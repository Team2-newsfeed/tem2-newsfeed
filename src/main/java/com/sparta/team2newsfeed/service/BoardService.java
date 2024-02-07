package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardService {
    //Repository 주입
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    //전체게시글 조회
    public ResponseEntity<?> getBoardAll() {
        return;
    }

    //단일게시글 조회
    public ResponseEntity<?> getBoardOne(Long boardId) {
        return;
    }

    //카테고리별 조회
    public ResponseEntity<?> getBoardCategory(String categoryName) {
        return;
    }

    //난이도별 조회
    public ResponseEntity<?> getBoardCookLevel(int cookLevel) {
        return;
    }

    //게시글 작성
    public ResponseEntity<?> addBoard(UserDetarilsImpl userDetarils,
                                      #DTO) {
        return;
    }

    //게시글 수정
    public ResponseEntity<?> updateBoard(Long boardId,
                                         UserDetarilsImpl userDetarils,
                                         #DTO) {
        return;
    }

    //게시글 삭제
    public ResponseEntity<?> deleteBoard(Long boardId,
                                         UserDetarilsImpl userDetarils) {
        return;
    }
}
