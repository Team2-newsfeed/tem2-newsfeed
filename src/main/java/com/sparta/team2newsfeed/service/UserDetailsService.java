package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.entity.User;
import com.sparta.team2newsfeed.imp.UserDetailsImpl;
import com.sparta.team2newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsService {
    private final UserRepository userRepository;
    public UserDetails getUserDetails(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Not Found" + username));
        return new UserDetailsImpl(user);

    }

    public static Long getLoginUserId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null){
            throw new IllegalArgumentException("로그인 유저 정보가 없음");
        }
        Long LoginId = Long.parseLong(authentication.getName());
        return LoginId;
    }



}