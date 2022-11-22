package com.kata.marketplace.controller;

import com.kata.marketplace.dto.AuthDto;
import com.kata.marketplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/auth")
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody AuthDto credentials) {
        return authenticationService.loginUser(credentials);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> saveUser(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authenticationService.saveUser(authDto));
    }

}
