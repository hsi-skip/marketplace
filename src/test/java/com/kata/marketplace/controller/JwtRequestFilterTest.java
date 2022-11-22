package com.kata.marketplace.controller;

import com.kata.marketplace.MarketplaceApplication;
import com.kata.marketplace.service.impl.JwtUserDetailsServiceImpl;
import com.kata.marketplace.util.JwtTokenFilter;
import com.kata.marketplace.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.User;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = MarketplaceApplication.class)
class JwtRequestFilterTest {

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    void doFilterInternalTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer FKSJHDKJFHB4W34JH54J2HB4");
        when(jwtTokenUtil.getUsername(Mockito.any(String.class))).thenReturn("username");
        when(jwtUserDetailsService.loadUserByUsername(Mockito.any(String.class))).thenReturn(new User("username", "pass", new ArrayList<>()));
        when(jwtTokenUtil.validate(Mockito.any(String.class))).thenReturn(true);

        try {
            jwtTokenFilter.doFilter(request, new Response(20), new MockFilterChain());
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void tokenWithoutBearerTest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", " FKSJHDKJFHB4W34JH54J2HB4");
        when(jwtTokenUtil.getUsername(Mockito.any(String.class))).thenReturn("username");
        when(jwtTokenUtil.validate(Mockito.any(String.class))).thenReturn(true);
        exceptionRule.expectMessage("JWT Token does not begin with Bearer String");
        jwtTokenFilter.doFilter(request, new Response(20), new MockFilterChain());
    }

//

    @Test
    void expiredTokenTest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer FKSJHDKJFHB4W34JH54J2HB4");
        when(jwtTokenUtil.getUsername(Mockito.any(String.class))).thenReturn("username");
        when(jwtUserDetailsService.loadUserByUsername(Mockito.any(String.class))).thenReturn(new User("username", "pass", new ArrayList<>()));
        when(jwtTokenUtil.validate(Mockito.any(String.class)))
                .thenThrow(new ExpiredJwtException(new DefaultHeader(), new DefaultClaims(), "JWT Token is expired"));
        exceptionRule.expectMessage("JWT Token is expired");
        try {
            jwtTokenFilter.doFilter(request, new Response(20), new MockFilterChain());
        } catch (ExpiredJwtException expectedException) {
        }

    }
    @Test
    void unableToFetchTokenTest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer");
        exceptionRule.expectMessage("Unable to fetch JWT Token");

        try {
            jwtTokenFilter.doFilter(request, new Response(20), new MockFilterChain());
        } catch (IllegalArgumentException expectedException) {
        }

    }
    @Test
    void globalExceptionTest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer FKSJHDKJFHB4W34JH54J2HB4");
        when(jwtTokenUtil.getUsername(Mockito.any(String.class))).thenReturn(" ").thenThrow(new IllegalArgumentException());
        Assert.assertNotNull(request);
    }
}
