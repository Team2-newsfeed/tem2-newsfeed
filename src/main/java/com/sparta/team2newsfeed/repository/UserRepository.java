package com.sparta.team2newsfeed.repository;

import com.sparta.team2newsfeed.dto.UserUpdateRequestDto;
import com.sparta.team2newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    void update(User user, UserUpdateRequestDto userUpdateRequestDto, PasswordEncoder passwordEncoder);
}
