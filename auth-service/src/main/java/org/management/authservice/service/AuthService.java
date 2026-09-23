package org.management.authservice.service;

import org.management.authservice.dto.LoginRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AuthService {
    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    boolean validateToken(String token);
}
