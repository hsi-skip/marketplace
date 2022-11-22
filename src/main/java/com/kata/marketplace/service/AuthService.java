package com.kata.marketplace.service;

import com.kata.marketplace.dto.AuthDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    ResponseEntity<Map<String, Object>> loginUser(AuthDto credentials);
    Map<String, Object> saveUser(AuthDto personalData);

}
