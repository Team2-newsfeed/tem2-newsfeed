package com.sparta.team2newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team2NewsfeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team2NewsfeedApplication.class, args);
    }

}
