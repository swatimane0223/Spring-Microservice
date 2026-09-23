package org.management.authservice.service;

import io.jsonwebtoken.JwtException;
import org.management.authservice.dto.LoginRequestDTO;
import org.management.authservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token =  userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(user1 -> jwtUtil.generateToken(user1.getEmail(), user1.getRole()));

        return token;
    }

    @Override
    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        } catch(JwtException e){
            return false;
        }
    }
}
