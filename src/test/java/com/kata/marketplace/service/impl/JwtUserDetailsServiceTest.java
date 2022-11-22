package com.kata.marketplace.service.impl;

import com.kata.marketplace.entity.User;
import com.kata.marketplace.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
  class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsServiceImpl jwtUserDetailsService;
    @Mock
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("elon");
        user.setPassword("password");

        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertNotNull(this.jwtUserDetailsService.loadUserByUsername("elon"));
    }
}
