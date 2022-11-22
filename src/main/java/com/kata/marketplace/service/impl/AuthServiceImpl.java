package com.kata.marketplace.service.impl;

import com.kata.marketplace.dto.AuthDto;
import com.kata.marketplace.entity.User;
import com.kata.marketplace.repository.UserRepository;
import com.kata.marketplace.service.AuthService;
import com.kata.marketplace.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    private static final String KEY = "token";
    final UserRepository userRepository;
    final JwtUserDetailsServiceImpl userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    final AuthenticationManager authenticationManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public ResponseEntity<Map<String, Object>> loginUser(AuthDto credentials) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            if (credentials == null) {
                throw new BadCredentialsException("");
            }
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername()
                    , credentials.getPassword()));

            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put(ERROR, false);
                responseMap.put(MESSAGE, "Logged In");
                responseMap.put(KEY, token);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put(ERROR, true);
                responseMap.put(MESSAGE, "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (BadCredentialsException e) {
            logger.debug("The username/password combination you provided are not valid. Please try again.");
            responseMap.put(ERROR, true);
            responseMap.put(MESSAGE, "Invalid Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (AuthenticationException e) {
            logger.debug("Authentication request failed", e);
            responseMap.put(ERROR, true);
            responseMap.put(MESSAGE, "User name (and /or) password you entered are incorrect");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            responseMap.put(ERROR, true);
            responseMap.put(MESSAGE, "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @Override
    public Map<String, Object> saveUser(AuthDto personalData) {

        Map<String, Object> responseMap = new HashMap<>();
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode(personalData.getPassword()));
        user.setUsername(personalData.getUsername());
        userRepository.save(user);

        responseMap.put(ERROR, false);
        responseMap.put("username", personalData.getUsername());
        responseMap.put(MESSAGE, "Account created successfully");
        return responseMap;
    }
}
