package com.sparta.team2newsfeed.entity;

import com.sparta.team2newsfeed.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String intro;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boardList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Likes> likesList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    //회원정보 대조용
    public User(String username, String name, String password, String email, String intro) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.intro = intro;
    }

    // 유저 정보 수정하기
    public void userUpdate(UserUpdateRequestDto userUpdateRequestDto, PasswordEncoder passwordEncoder) {
        if (userUpdateRequestDto.getPassword() != null) {
            this.password = passwordEncoder.encode(userUpdateRequestDto.getPassword());
        }
        if (userUpdateRequestDto.getUsername() != null) {
            this.username = userUpdateRequestDto.getUsername();
        }
        if (userUpdateRequestDto.getEmail() != null) {
            this.email = userUpdateRequestDto.getEmail();
        }
        if (userUpdateRequestDto.getIntro() != null) {
            this.intro = userUpdateRequestDto.getIntro();
        }
    }
}
