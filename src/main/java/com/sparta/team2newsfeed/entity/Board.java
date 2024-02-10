package com.sparta.team2newsfeed.entity;

import com.sparta.team2newsfeed.dto.AddBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "board")
public class Board extends Timestemped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int cookLevel;

    //1:N 양방향
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JoinColumn(name = "Like_id")
    private List<Like> likes = new ArrayList<>();

    // BoardService 에서 보드Dto 와 유저 ID를 받기 위한 생성자
    public Board(AddBoardRequestDto addBoardRequestDto, User user) {
        this.title = addBoardRequestDto.getTitle();
        this.body = addBoardRequestDto.getBody();
        this.category = addBoardRequestDto.getCategory();
        this.cookLevel = addBoardRequestDto.getCookLevel();
        this.user = user;
    }

    // 게시물 수정시 사용
    public void update(AddBoardRequestDto addBoardRequestDto) {
        this.title = addBoardRequestDto.getTitle();
        this.body = addBoardRequestDto.getBody();;
        this.category = addBoardRequestDto.getCategory();
        this.cookLevel = addBoardRequestDto.getCookLevel();
    }
}
