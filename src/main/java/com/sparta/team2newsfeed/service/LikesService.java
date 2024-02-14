package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.dto.StatusResponseDto;
import com.sparta.team2newsfeed.entity.Board;
import com.sparta.team2newsfeed.entity.Likes;
import com.sparta.team2newsfeed.entity.User;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.repository.BoardRepository;
import com.sparta.team2newsfeed.repository.LikesRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;

    public LikesService(LikesRepository likesRepository, BoardRepository boardRepository) {
        this.likesRepository = likesRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResponseEntity<StatusResponseDto> addLikes(UserDetailsImpl userDetails,
                                                      Long boardId) {

        if (findBoard(boardId).isPresent()) {
            Board board = findBoard(boardId).get();
            if (findMyBoard(board, userDetails)) {
                return new ResponseEntity<>(
                        new StatusResponseDto("본인 게시글엔 누를수 없습니다.", 400),
                        HttpStatusCode.valueOf(400));
            } else {
                Likes likes = likesRepository.findLikesByBoard_IdAndUser_IdEquals(boardId, userDetails.getUser().getId());
                if (likes != null) {
                    likesRepository.delete(likes);
                    return new ResponseEntity<>(
                            new StatusResponseDto("좋아요 취소", 200),
                            HttpStatusCode.valueOf(200));
                } else {
                    Likes newLikes = new Likes(userDetails, board);
                    likesRepository.save(newLikes);
                    return new ResponseEntity<>(
                            new StatusResponseDto("좋아요", 200),
                            HttpStatusCode.valueOf(200));
                }
            }
        } else {
            return new ResponseEntity<>(
                    new StatusResponseDto("게시글이 존재하지 않습니다.", 400),
                    HttpStatusCode.valueOf(400));
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
