package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    //Repository 주입
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public RequestEntity<?> userSignup(
//            #DOT
            ) {
        return null;
    }

    //로그인
    public RequestEntity<?> userLogin(
//            UserDetarilsImpl userDetarils
    ) {
        return null;
    }

    //로그아웃
    public void userLogout(
//            UserDetarilsImpl userDetarils
    ) {
    }

    //회원수정
    public RequestEntity<?> userUpdate(Long boardId
//            , UserDetarilsImpl userDetarils
    ) {
        return null;
    }
}
