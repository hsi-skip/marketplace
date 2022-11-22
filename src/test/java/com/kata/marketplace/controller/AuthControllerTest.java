package com.kata.marketplace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.marketplace.MarketplaceApplication;
import com.kata.marketplace.dto.AuthDto;
import com.kata.marketplace.repository.UserRepository;
import com.kata.marketplace.service.impl.JwtUserDetailsServiceImpl;
import com.kata.marketplace.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MarketplaceApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class AuthControllerTest {

    private static final String DEFAULT_USERNAME = "username";
    private static final CharSequence DEFAULT_PASSWORD = "password";



    @Autowired
    ModelMapper mapper;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUserDetailsServiceImpl userDetailsService;

    @Mock
    Authentication authentication;

    @Mock
    UserDetails userDetails;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginOk() throws Exception {
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("token");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(asJsonString(new AuthDto("elon", "twitter")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Logged In"));
    }

    @Test
    void testLoginNotAuthenticated() throws Exception {
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(asJsonString(new AuthDto("elon", "twitter")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testLoginBadCredentialsException() throws Exception {
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException(""));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(asJsonString(new AuthDto("elon", "twitter")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Credentials"));
    }

    @Test
    void testLoginAuthenticationException() throws Exception {
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException(""));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(asJsonString(new AuthDto("elon", "twitter")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User name (and /or) password you entered are incorrect"));
    }

    @Test
    void testLoginException() throws Exception {
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(asJsonString(new AuthDto("elon", "twitter")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"));
    }

    @Test
    void saveUser() throws Exception {

        when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("token");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(asJsonString(new AuthDto("username", "password")))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Account created successfully")).andExpect(MockMvcResultMatchers.jsonPath("username")
                        .value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error")
                        .value(false));


    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
