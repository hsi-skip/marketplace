package com.kata.marketplace.util;

import com.kata.marketplace.MarketplaceApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@SpringBootTest(classes = MarketplaceApplication.class)
class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void jwtTokenTest(){
        User userDetails = new User("username", "pass", new ArrayList<>());
        String token = jwtTokenUtil.generateToken(userDetails);

        Assertions.assertNotNull(token);
        Assertions.assertNotNull(jwtTokenUtil.getUsername(token));
        Assertions.assertTrue(jwtTokenUtil.validate(token));
    }

}
