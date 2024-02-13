package com.sparta.team2newsfeed.service;


import com.sparta.team2newsfeed.CommonResponseDto;
import com.sparta.team2newsfeed.dto.BoardRequestDto;
import com.sparta.team2newsfeed.dto.BoardResponseDto;
import com.sparta.team2newsfeed.entity.Board;
import com.sparta.team2newsfeed.entity.Category;
import com.sparta.team2newsfeed.entity.User;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        //List에 board를 모두 담음
        List<BoardResponseDto> boardList = boardRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(BoardResponseDto::new).toList();
        if(boardList.isEmpty()){
            return new ResponseEntity<>("현재 등록된 게시물이 없습니다.", HttpStatusCode.valueOf(400));
        } else {
            // 모든 게시물을 Controller로 리턴
            return new ResponseEntity<>(boardList, HttpStatusCode.valueOf(200));
        }
    }

    //단일게시글 조회
    public ResponseEntity<?> getBoardOne(Long boardId) {
        try {
            //조회한 게시글의 ID가 있으면 담아서 Controller로 전달
            Board board = boardRepository.findById(boardId).orElseThrow(()
                    -> new IllegalArgumentException("해당하는 게시물이 없습니다."));
            return new ResponseEntity<>(new BoardResponseDto(board),HttpStatusCode.valueOf(200));
        } catch (IllegalArgumentException e){
            //Exception 발생하면 에러메세지와 상태코드 Controller로 전달
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }
//
    //카테고리별 조회
    public ResponseEntity<?> getBoardCategory(String categoryName) {
        //입력된 값이 맞는지 확인하는 메소드
        if(isCategoryYn(categoryName)){
            List<BoardResponseDto> cateBoardList =
                    boardRepository.findBoardsByCategoryEqualsOrderByCreatedAtDesc(categoryName.toUpperCase()).stream()
                            .map(BoardResponseDto::new).toList();
            if(cateBoardList.isEmpty()) {
                //값은 일치하지만 List가 null 일때
                return new ResponseEntity<>(categoryName + " 카테고리에 해당하는 레시피가 없습니다.",HttpStatusCode.valueOf(400));
            } else {
                //값이 일치하면서 List가 null이 아닐때
                return new ResponseEntity<>(cateBoardList,HttpStatusCode.valueOf(200));
            }
        } else {
            //일치하는 값이 없으면 에러메세지 포함해서 Controller 전달
            return new ResponseEntity<>("현재 생성된 카테고리는 " + Arrays.toString(Category.values()) + "입니다.",HttpStatusCode.valueOf(400));
        }
    }

    //난이도별 조회
    public ResponseEntity<?> getBoardCookLevel(int cookLevel) {
        //난이도 1~5사이인지 확인
        if(1 <= cookLevel && cookLevel <= 5){
            List<BoardResponseDto> levelBoardList =
                    boardRepository.findBoardsByCookLevelEqualsOrderByCreatedAtDesc(cookLevel).stream()
                            .map(BoardResponseDto::new).toList();
            if(levelBoardList.isEmpty()){
                //난이도는 맞는데 리스트가 null일때
                return new ResponseEntity<>("난이도 " + cookLevel + " 에 해당하는 게시글이 없습니다.",HttpStatusCode.valueOf(400));
            } else {
                //난이도는 맞는데 리스트가 null이 아닐때
                return new ResponseEntity<>(levelBoardList, HttpStatusCode.valueOf(200));
            }
        } else {
            //난이도가 벗어나면 에러메세지 포함해서 Controller 전달
            return new ResponseEntity<>("레시피의 난이도는 1~5까지 입니다.",HttpStatusCode.valueOf(400));
        }

    }

    //게시글 작성
    public ResponseEntity<?> addBoard(
            UserDetailsImpl userDetails,
            BoardRequestDto dto
            ) {
        // 새로운 보드 entity 에 보드 정보와 유저를 넣어서 저장
        Board addedBoard = boardRepository.save(new Board(dto, userDetails.getUser()));

        return new ResponseEntity<>(new BoardResponseDto(addedBoard, userDetails.getUser()), HttpStatusCode.valueOf(200));
    }

    //게시글 수정
    public ResponseEntity<?> updateBoard(Long boardId,
                                         UserDetailsImpl userDetails,
                                         BoardRequestDto boardRequestDto
    ) {
        if (findBoard(boardId).isPresent()) {
            // optional
            Board board = findBoard(boardId).get();

            // 본인 게시글인지 확인
            if(findMyBoard(board, userDetails)) {
                board.update(boardRequestDto);
                Board updatedBoard = boardRepository.save(board);
                return new ResponseEntity<>(new BoardResponseDto(updatedBoard, userDetails.getUser()), HttpStatusCode.valueOf(201));
            } else { // 본인 게시글이 아닌 경우
                return new ResponseEntity<>(new CommonResponseDto("해당 게시글 작성자만 수정 가능합니다.", 400), HttpStatusCode.valueOf(400));
            }
        } else { // boardId 에 해당하는 게시물이 없는 경우
            return new ResponseEntity<>(new CommonResponseDto("찾고자 하는 ID의 게시글이 존재하지 않습니다.",400), HttpStatusCode.valueOf(400));
        }
    }

    //게시글 삭제
    public ResponseEntity<?> deleteBoard(Long boardId,
                                         UserDetailsImpl userDetails
    ) { // 삭제 하고자 하는 게시글 찾기
        if (findBoard(boardId).isPresent()) {
            Board board = findBoard(boardId).get();
            // 해당 게시글 작성자 확인 로직
            if (findMyBoard(board, userDetails)) {
                boardRepository.delete(board);
                return new ResponseEntity<>(new CommonResponseDto("게시물 삭제 완료", 200), HttpStatusCode.valueOf(200));
            } else { // 본인 게시글이 아닌 경우
                 return new ResponseEntity<>(new CommonResponseDto("해당 게시글 작성자만 삭제 가능합니다.", 400), HttpStatusCode.valueOf(400));
            }
        } else { // boardId 에 해당하는 게시물이 없는 경우
            return new ResponseEntity<>(new CommonResponseDto("찾고자 하는 ID의 게시글이 존재하지 않습니다.",400), HttpStatusCode.valueOf(400));
        }
    }

    //입력된 Category가 맞는지 검증 로직
    private boolean isCategoryYn(String categoryName){
        //입력받은 값을 대문자로 치환
        String upperCategoryName = categoryName.toUpperCase();
        //해당 String이 Enum안에 있을 경우 true, 아닐경우 false를 반환
        try{
            Category category = Category.valueOf(upperCategoryName);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    //게시글 작성자 본인인지 확인
    private boolean findMyBoard(Board board, UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        User boardUser = board.getUser();

        return loginUser.getUsername().equals(boardUser.getUsername()) &&
                loginUser.getPassword().equals(boardUser.getPassword());
    }

    //존재하는 게시물인지 확인
    private Optional<Board> findBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }
}
