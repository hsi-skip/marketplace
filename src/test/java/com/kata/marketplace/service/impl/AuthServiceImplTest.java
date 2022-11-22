package com.kata.marketplace.service.impl;


import com.kata.marketplace.dto.AuthDto;
import com.kata.marketplace.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
 class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;
    @Mock
    private JwtUserDetailsServiceImpl userDetailsService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private AuthenticationManager authenticationManager;

    @Disabled
    @Test
     void testLoginUserWhenAutenticatedEqualsTrue() {
        AuthDto credentials = new AuthDto();
        credentials.setPassword("password");
        credentials.setUsername("elon");

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder();
        UserDetails userDetails = userBuilder.username("elon").password("password").build() ;
        Authentication authentication =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(this.userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
        Mockito.when(this.jwtTokenUtil.generateToken(Mockito.any())).thenReturn("AccessToken");
        Assertions.assertNotNull(this.authServiceImpl.loginUser(credentials));
    }

    @Test
    void testLoginUserWhenAutenticationNotSet() {
        AuthDto credentials = new AuthDto();
        credentials.setPassword("password");
        credentials.setUsername("elon");
        Assertions.assertNotNull(this.authServiceImpl.loginUser(credentials));
    }

    @Test
    void testLoginUserWhenAutenticatedEqualsFalse() {
        AuthDto credentials = new AuthDto();
        credentials.setPassword("password");
        credentials.setUsername("elon");
        Authentication authentication =  new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword());
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Assertions.assertNotNull(this.authServiceImpl.loginUser(credentials));
    }

    @Test
    void testLoginUserAndThrowBadCredentialsException() {
        AuthDto credentials = null;
        Assertions.assertNotNull(this.authServiceImpl.loginUser(credentials));
    }

    @Disabled
    @Test
    void testSaveUser() {
        com.kata.marketplace.entity.User user = new com.kata.marketplace.entity.User();
        user.setId(1L);
        user.setUsername("elon");
        AuthDto personalData = new AuthDto();
        personalData.setPassword("password");
        personalData.setUsername("elon");
        Assertions.assertNotNull(this.authServiceImpl.saveUser(personalData));
    }

}
