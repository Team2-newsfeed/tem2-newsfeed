package com.sparta.team2newsfeed;

import com.sparta.team2newsfeed.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {

    @Test
    @DisplayName(value = "Enum Test")
    void test1(){
        Category[] category = Category.values();
        System.out.println("category[1] = " + category[1]);

    }
}
