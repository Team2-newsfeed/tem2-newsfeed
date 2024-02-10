package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.entity.Board;
import com.sparta.team2newsfeed.entity.Likes;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.repository.BoardRepository;
import com.sparta.team2newsfeed.repository.LikesRepository;
import org.springframework.stereotype.Service;

@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;

    public LikesService(LikesRepository likesRepository, BoardRepository boardRepository) {
        this.likesRepository = likesRepository;
        this.boardRepository = boardRepository;
    }

    public void voidLikes(UserDetailsImpl userDetails, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("해당하는 게시물이 존재하지 않습니다."));
//        return Optional.of(new ResponseEntity<>(
//                new StatusResponseDto("해당 게시글이 존재하지 않습니다.", 400),
//                HttpStatusCode.valueOf(202)));

        Likes likes = likesRepository.findLikesByBoard_IdAndUser_IdEquals(boardId, userDetails.getUser().getId());
        if (likes != null) {
            likesRepository.delete(likes);
        } else {
            Likes newLikes = new Likes(userDetails, board);
            likesRepository.save(newLikes);
        }
    }
}
