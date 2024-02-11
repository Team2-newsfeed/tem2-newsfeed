package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.dto.CommentRequestDto;
import com.sparta.team2newsfeed.dto.CommentResponseDto;
import com.sparta.team2newsfeed.dto.StatusResponseDto;
import com.sparta.team2newsfeed.entity.Board;
import com.sparta.team2newsfeed.entity.Comment;
import com.sparta.team2newsfeed.entity.User;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.repository.BoardRepository;
import com.sparta.team2newsfeed.repository.CommentRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    //comment, board repository 주입 (board 는 실존유무 검증때문에 주입)
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    //comment 생성
    public ResponseEntity<?> addComment(UserDetailsImpl userDetails,
                                        CommentRequestDto commentRequestDto,
                                        Long boardId) {
        //게시글 존재여부 확인
        if (findBoard(boardId).isPresent()) {
            Board board = findBoard(boardId).get();
            //comment 생성 후 전달
            Comment addComment = commentRepository.save(new Comment(commentRequestDto, board, userDetails.getUser()));
            return new ResponseEntity<>(new CommentResponseDto(addComment, board.getId(), userDetails.getUser()),
                    HttpStatusCode.valueOf(200));
        } else {
            //없으면 아래메세지 전달
            return new ResponseEntity<>(
                    new StatusResponseDto("해당하는 게시물이 없습니다.", 400),
                    HttpStatusCode.valueOf(400));
        }
    }

    //comment 수정
    public ResponseEntity<?> updateComment(UserDetailsImpl userDetails,
                                           CommentRequestDto commentRequestDto,
                                           Long boardId,
                                           Long commentId) {
        //게시글 존재여부 확인
        if (findBoard(boardId).isPresent()) {
            //comment 존재여부 확인
            if (findComment(commentId).isPresent()) {
                Comment comment = findComment(commentId).get();
                //본인 comment인지 확인
                if (!findMyComment(comment, userDetails)) {
                    //작성자 본인 아닐 경우
                    return new ResponseEntity<>(
                            new StatusResponseDto("작성자만 수정이 가능합니다.", 400),
                            HttpStatusCode.valueOf(400));
                } else {
                    //작성자 본인일 경우 수정해서 전달
                    comment.update(commentRequestDto);
                    Comment updateComment = commentRepository.save(comment);
                    return new ResponseEntity<>(
                            new CommentResponseDto(updateComment, boardId, userDetails.getUser()),
                            HttpStatusCode.valueOf(200));
                }
            } else {
                //comment 없으면 아래메세지 전달
                return new ResponseEntity<>(
                        new StatusResponseDto("해당하는 댓글이 없습니다.", 400),
                        HttpStatusCode.valueOf(400));
            }
        } else {
            //게시물 없으면 아래메세지 전달
            return new ResponseEntity<>(
                    new StatusResponseDto("해당하는 게시물이 없습니다.", 400),
                    HttpStatusCode.valueOf(400));
        }

    }

    //comment 삭제
    public ResponseEntity<StatusResponseDto> deleteComment(UserDetailsImpl userDetails,
                                                           Long boardId,
                                                           Long commentId) {
        //게시글 존재여부 확인
        if (findBoard(boardId).isPresent()) {
            //comment 존재여부 확인
            if (findComment(commentId).isPresent()) {
                Comment comment = findComment(commentId).get();
                //본인 comment인지 확인
                if (!findMyComment(comment, userDetails)) {
                    //작성자 본인 아닐 경우
                    return new ResponseEntity<>(
                            new StatusResponseDto("작성자만 수정이 가능합니다.", 400),
                            HttpStatusCode.valueOf(400));
                } else {
                    //작성자 본인일 경우 수정해서 전달
                    commentRepository.delete(comment);
                    return new ResponseEntity<>(
                            new StatusResponseDto("댓글이 삭제 되었습니다.", 400),
                            HttpStatusCode.valueOf(400));
                }
            } else {
                //comment 없으면 아래메세지 전달
                return new ResponseEntity<>(
                        new StatusResponseDto("해당하는 댓글이 없습니다.", 400),
                        HttpStatusCode.valueOf(400));
            }
        } else {
            //게시물 없으면 아래메세지 전달
            return new ResponseEntity<>(
                    new StatusResponseDto("해당하는 게시물이 없습니다.", 400),
                    HttpStatusCode.valueOf(400));
        }
    }

    //게시글 작성자 본인인지 확인
    private boolean findMyComment(Comment comment, UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        User commentUser = comment.getUser();

        return loginUser.getUsername().equals(commentUser.getUsername()) &&
                loginUser.getPassword().equals(commentUser.getPassword());
    }

    //존재하는 게시물인지 확인
    private Optional<Board> findBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }

    //존재하는 comment인지 확인
    private Optional<Comment> findComment(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
